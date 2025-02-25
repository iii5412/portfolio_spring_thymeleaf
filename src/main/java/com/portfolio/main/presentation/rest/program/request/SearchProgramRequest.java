package com.portfolio.main.presentation.rest.program.request;

import com.portfolio.main.application.program.dto.SearchProgram;
import com.portfolio.main.application.program.exception.InvalidSearchParameterException;
import com.portfolio.main.common.util.page.PageDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Getter
@Setter
public class SearchProgramRequest extends PageDto {
    private String id;
    private String programName;
    private String url;

    public void validate() {
        final InvalidSearchParameterException invalidSearchParameterException = new InvalidSearchParameterException();

        try {
            if (StringUtils.hasText(id))
                Long.parseLong(id);
        } catch (NumberFormatException e) {
            invalidSearchParameterException.addValidation("searchInput", "숫자만 입력해주세요.");
        }

        if (invalidSearchParameterException.hasErrors())
            throw invalidSearchParameterException;
    }

    public SearchProgram toSearchProgram() {
        return SearchProgram.builder()
                .id(StringUtils.hasText(id) ? Long.parseLong(id) : null)
                .programName(programName)
                .url(url)
                .page(getPage())
                .size(getSize())
                .sortFields(getSortFields())
                .sort(getSorts())
                .build();
    }
}
