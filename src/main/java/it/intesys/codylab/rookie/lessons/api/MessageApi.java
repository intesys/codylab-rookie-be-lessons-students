package it.intesys.codylab.rookie.lessons.api;

import it.intesys.codylab.rookie.lessons.dto.MessageDto;
import it.intesys.codylab.rookie.lessons.dto.MessageFilterDto;
import it.intesys.codylab.rookie.lessons.service.MessageService;
import jakarta.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MessageApi extends RookieApi {
    public static final String MESSAGE_PATH = "/api/message";
    @Autowired
    MessageService messageService;

    Logger logger = LoggerFactory.getLogger(MessageApi.class);

    @PostMapping(MESSAGE_PATH)
    MessageDto createMessage (@RequestBody MessageDto messageDto) {
        logger.info("Creating message alias {}", messageDto.getId());
        return messageService.createMessage(messageDto);
    }

    @PutMapping(MESSAGE_PATH + "/{id}")
    MessageDto updateMessage (@PathVariable("id") Long id, @RequestBody MessageDto messageDto) {
        messageDto.setId(id);
        return messageService.updateMessage(messageDto);
    }

    @GetMapping(MESSAGE_PATH + "/{id}")
    MessageDto getMessage (@PathVariable("id") Long id) {
        return messageService.getMessage(id);
    }

    @DeleteMapping(MESSAGE_PATH + "/{id}")
    void deleteMessage (@PathVariable("id") Long id) {
        messageService.deleteMessage(id);
    }

    @PostMapping(MESSAGE_PATH + "/filter")
    List<MessageDto> getMessages (@Nullable @RequestParam("page") Integer page, @Nullable @RequestParam("size") Integer size,
                                  @Nullable @RequestParam("sort") String sort, @Nullable @RequestBody MessageFilterDto filter) {
        if (page == null)
            page = 0;

        if (size == null)
            size = DEFAULT_SIZE;

        if (sort == null || sort.isBlank())
            sort = "text";

        return messageService.getMessages (page, size, sort, filter);
    }
}
