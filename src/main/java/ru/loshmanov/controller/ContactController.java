package ru.loshmanov.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.loshmanov.applied.Message;
import ru.loshmanov.applied.UrlUtil;
import ru.loshmanov.entity.Contact;
import ru.loshmanov.service.ContactService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

@Controller
@RequestMapping("contacts")
public class ContactController {

    private static final int PAGE_SIZE = 10;
    private static final String SORTING_FIELD = "id";

    @Autowired
    private ContactService contactService;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String list(Model uiModel) {
        return "redirect:/contacts/page/1";
    }

    @RequestMapping(value = "/page/{page}", method = RequestMethod.GET)
    public String pageList(@PathVariable("page") int page, Model uiModel) {
        if (page == 0) {
            return "redirect:/contacts/page/1";
        }
        PageRequest request = new PageRequest(page - 1, PAGE_SIZE, Sort.Direction.ASC, SORTING_FIELD);
        Page<Contact> contactPage = contactService.findAll(request);
        if (page == 1 && !contactPage.hasContent()) {
            uiModel.addAttribute("page", 1);
            uiModel.addAttribute("contacts", null);
            return "contacts/list";
        }
        if (contactPage.hasContent()) {
            uiModel.addAttribute("page", page);
            uiModel.addAttribute("contacts", contactPage.getContent());
            return "contacts/list";
        }
        return "redirect:/contacts/page/" + (page - 1);
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model uiModel) {
        Contact contact = contactService.findById(id);
        uiModel.addAttribute("contact", contact);
        return "contacts/show";
    }

    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.POST)
    public String update(@Valid Contact contact, BindingResult bindingResult, Model uiModel,
                         HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes,
                         Locale locale) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("message", new Message("error",
                    messageSource.getMessage("contact_save_fail", new Object[]{}, locale)));
            uiModel.addAttribute("contact", contact);
            return "contacts/update";
        }
        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message("success",
                messageSource.getMessage("contact_save_success", new Object[]{}, locale)));
        contactService.save(contact);
        return "redirect:/contacts/id/" + UrlUtil.encodeUrlPathSegment(contact.getId().toString(),
                httpServletRequest);
    }

    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("contact", contactService.findById(id));
        return "contacts/update";
    }

    @RequestMapping(params = "form", method = RequestMethod.POST)
    public String create(@Valid Contact contact, BindingResult bindingResult, Model uiModel,
                         HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes,
                         Locale locale, @RequestParam(value = "file", required = false) Part file) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("message", new Message("error",
                    messageSource.getMessage("contact_save_fail", new Object[]{}, locale)));
            uiModel.addAttribute("contact", contact);
            return "contacts/create";
        }
        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message("success",
                messageSource.getMessage("contact_save_success", new Object[]{}, locale)));
        if (file != null) {
            byte[] fileContent = null;
            try {
                InputStream inputStream = file.getInputStream();
                fileContent = IOUtils.toByteArray(inputStream);
                contact.setPhoto(fileContent);

            } catch (IOException e) {
            }
        }
        contactService.save(contact);
        return "redirect:/contacts/id/" + UrlUtil.encodeUrlPathSegment(contact.getId().toString(),
                httpServletRequest);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        Contact contact = new Contact();
        uiModel.addAttribute("contact", contact);
        return "contacts/create";
    }

    @RequestMapping(value = "/{id}", params = "delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id, Model uiModel) {
        contactService.delete(contactService.findById(id));
        return "redirect:/contacts/page/1";
    }

    @RequestMapping(value = "/photo/{id}", method = RequestMethod.GET)
    @ResponseBody
    public byte[] downloadPhoto(@PathVariable("id") Long id) {
        Contact contact = contactService.findById(id);
        return contact.getPhoto();
    }

}
