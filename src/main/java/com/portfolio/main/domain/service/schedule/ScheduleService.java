package com.portfolio.main.domain.service.schedule;

import com.portfolio.main.domain.model.schedule.Category;
import com.portfolio.main.domain.model.schedule.exception.CategoryNotFoundException;
import com.portfolio.main.domain.repository.schedule.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ScheduleService {
    private final CategoryRepository repository;

    public Category findById(final Long id) {
        return repository.findById(id).orElseThrow(CategoryNotFoundException::new);
    }

//    public Long createCategory(final CreateCategory createCategory) {
//        return this.repository.save(createCategory.toEntity()).getId();
//    }
}
