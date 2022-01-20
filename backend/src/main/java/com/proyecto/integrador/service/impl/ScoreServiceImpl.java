package com.proyecto.integrador.service.impl;


import com.proyecto.integrador.DTO.ScoreDTO;
import com.proyecto.integrador.DTO.UserResponseDTO;
import com.proyecto.integrador.exceptions.BadRequestException;
import com.proyecto.integrador.exceptions.FindByIdException;
import com.proyecto.integrador.persistence.entity.Score;
import com.proyecto.integrador.persistence.repository.IScoreRepository;
import com.proyecto.integrador.service.IScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ScoreServiceImpl implements IScoreService {
    private final Logger logger = Logger.getLogger(ScoreServiceImpl.class);
    @Autowired
    IScoreRepository scoresRepository;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    ProductServiceImpl productService;

    public Double average(Integer idProduct) {
        logger.debug("Iniciando método promedio de todas las puntuaciones");

        List<Score> scores = scoresRepository.findByProductId(idProduct);
        Double totalScore = 0.0;
        if (scores == null || scores.size() == 0) {
            return totalScore;
        }
        for (Score score : scores) {
            totalScore += score.getScore();
        }
        return totalScore / scores.size();
    }

    @Override
    public List<ScoreDTO> findAll() throws FindByIdException {
        logger.debug("Iniciando método buscar todas las puntuaciones");
        List<ScoreDTO> scoresList = new ArrayList<>();
        for (Score score : scoresRepository.findAll()) {
            ScoreDTO scoreDTO = score.toDto();

            scoreDTO.setUserEmail(userService.findById(score.getIdUser()).getEmail());
            scoresList.add(scoreDTO);
        }
        logger.debug("Terminó la ejecución del método buscar todas las puntuaciones");
        return scoresList;
    }

    @Override
    public ScoreDTO save(ScoreDTO score) throws FindByIdException, BadRequestException {
        logger.debug("Iniciando método guardar puntuación");
        Score scoreEntity = score.toEntity();
        scoreEntity.setIdUser(userService.findByEmail(score.getUserEmail()).getId());
        if(findByUserAndProduct(score.getUserEmail(),score.getProductId()) == null) {
            scoresRepository.save(scoreEntity);
        }
        logger.debug("Terminó la ejecución del método guardar puntuación");
        return findByUserAndProduct(score.getUserEmail(),score.getProductId());
    }

    @Override
    public ScoreDTO findById(Integer id) throws FindByIdException {
        logger.debug("Iniciando método buscar producto por ID");
        if (!scoresRepository.existsById(id)) {
            throw new FindByIdException("No existe una puntuación con el id ingresado");
        }
        Score foundScore = scoresRepository.findById(id).get();
        logger.debug("Terminó la ejecución del método buscar puntuación por ID");
        return foundScore.toDto();
    }

    @Override
    public void deleteById(Integer id) throws FindByIdException {
        logger.debug("Iniciando método eliminar puntuación por ID");
        if (!scoresRepository.existsById(id)) {
            throw new FindByIdException("No existe una puntuación con el id ingresado");
        }
        logger.debug("Terminó la ejecución del método eliminar puntuación por ID");
        scoresRepository.deleteById(id);
    }

    @Override
    public ScoreDTO update(ScoreDTO score) throws FindByIdException {
        logger.debug("Iniciando método actualizar puntuación");
        if (!scoresRepository.existsById(score.getIdScore())) {
            throw new FindByIdException("No existe una puntuación con el id ingresado");
        }
        Score score1 = scoresRepository.findById(score.getIdScore()).get();
        score1.setIdUser(userService.findByEmail(score.getUserEmail()).getId());
        score1.setScore(score.getScore());
        logger.debug("Terminó la ejecución del método actualizar puntuación");
        scoresRepository.save(score1);

        return score;
    }

    @Override
    public Set<ScoreDTO> findAllByIdProduct(Integer idProduct) {
        return scoresRepository.findByProductId(idProduct).stream().map(Score::toDto).collect(Collectors.toSet());
    }

    @Override
    public ScoreDTO findByUserAndProduct(String email, Integer idProduct) throws BadRequestException {
        logger.debug("Iniciando método obtener puntuacion por usuario y producto");
        UserResponseDTO user = userService.findByEmail(email);
        if (user == null) {
            throw new BadRequestException("El usuario no existe");
        }
        List<ScoreDTO> scoresByUser = scoresRepository.findByIdUser(user.getId()).stream().map(Score::toDto).collect(Collectors.toList());
        ScoreDTO scoreDTOGuardado = null;
        for (ScoreDTO scoreDTO : scoresByUser) {
            if (Objects.equals(scoreDTO.getProductId(), idProduct)) {
                scoreDTOGuardado = scoreDTO;
            }
        }
        logger.debug("Terminó la ejecución del método obtener puntuacion por usuario y producto");
        return scoreDTOGuardado;
    }
}
