package ru.vsu.csf.asashina.cinemaback.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.csf.asashina.cinemaback.exceptions.MaxScreenNumberException;
import ru.vsu.csf.asashina.cinemaback.exceptions.ObjectNotExistsException;
import ru.vsu.csf.asashina.cinemaback.exceptions.SessionsExistException;
import ru.vsu.csf.asashina.cinemaback.mappers.ScreenMapper;
import ru.vsu.csf.asashina.cinemaback.models.dtos.screen.ScreenDTO;
import ru.vsu.csf.asashina.cinemaback.models.dtos.screen.ScreenPageDTO;
import ru.vsu.csf.asashina.cinemaback.models.entities.ScreenEntity;
import ru.vsu.csf.asashina.cinemaback.models.request.ScreenRequest;
import ru.vsu.csf.asashina.cinemaback.repositories.ScreenRepository;

@Service
@RequiredArgsConstructor
public class ScreenService {

    private final SeatService seatService;

    private final ScreenRepository screenRepository;

    private final ScreenMapper screenMapper;

    @Value("${screen.maxScreenNumber}")
    private Integer maxScreenNumber;

    public Page<ScreenPageDTO> getScreenInPages(Integer pageNumber, Integer size) {
        var pageable = PageRequest.of(pageNumber, size, Sort.by("screenNumber"));
        Page<ScreenEntity> pages = screenRepository.findAll(pageable);
        return pages.map(screenMapper::toPageDTOFromEntity);
    }

    public ScreenDTO getScreenById(Long screenId) {
        ScreenEntity entity = screenRepository.findById(screenId).orElseThrow(
                () -> new ObjectNotExistsException("Screen with following ID does not exist")
        );
        return screenMapper.toDTOFromEntity(entity);
    }

    @Transactional
    public void createScreen(ScreenRequest request) {
        long lastScreenNumber = screenRepository.count();
        if (lastScreenNumber + 1 == maxScreenNumber) {
            throw new MaxScreenNumberException("Max screen number reached");
        }
        ScreenEntity entity = screenRepository.save(screenMapper.fromRequestToEntity(request, (int) lastScreenNumber));
        seatService.createSeats(entity);
    }

    @Transactional
    public void deleteScreen(Long screenId) {
        ScreenDTO screen = getScreenById(screenId);
        if (screen.getSessions() != null && !screen.getSessions().isEmpty()) {
            throw new SessionsExistException("Cannot delete screen due to existing sessions");
        }
        screenRepository.deleteById(screenId);
    }
}
