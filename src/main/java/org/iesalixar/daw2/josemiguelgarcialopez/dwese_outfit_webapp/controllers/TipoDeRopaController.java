package org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_webapp.controllers;

import jakarta.validation.Valid;
import org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_webapp.entities.TipoDeRopa;
import org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_webapp.repositories.TipoDeRopaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * Controlador que maneja las operaciones CRUD para la entidad `TipoDeRopa`.
 */
@Controller
@RequestMapping("/tipos-de-ropa")
public class TipoDeRopaController {

    private static final Logger logger = LoggerFactory.getLogger(TipoDeRopaController.class);

    @Autowired
    private TipoDeRopaRepository tipoDeRopaRepository;

    @Autowired
    private MessageSource messageSource;

    @GetMapping
    public String listTipoDeRopa(Model model) {
        logger.info("Solicitando la lista de todos los tipos de ropa...");
        List<TipoDeRopa> listTipoDeRopa = tipoDeRopaRepository.findAll();
        model.addAttribute("listTipoDeRopa", listTipoDeRopa);
        return "tipoDeRopa"; // Nombre de la plantilla Thymeleaf a renderizar
    }

    @GetMapping("/new")
    public String showNewForm(Model model) {
        model.addAttribute("tipoDeRopa", new TipoDeRopa());
        return "tipoDeRopa-form";
    }

    @GetMapping("/edit")
    public String showEditForm(@RequestParam("id") Long id, Model model) {
        Optional<TipoDeRopa> tipoDeRopa = tipoDeRopaRepository.findById(id);
        if (tipoDeRopa.isEmpty()) {
            model.addAttribute("errorMessage", "Tipo de ropa no encontrado");
            return "redirect:/tipos-de-ropa";
        }
        model.addAttribute("tipoDeRopa", tipoDeRopa.get());
        return "tipoDeRopa-form";
    }

    @PostMapping("/insert")
    public String insertTipoDeRopa(@Valid @ModelAttribute("tipoDeRopa") TipoDeRopa tipoDeRopa, BindingResult result, RedirectAttributes redirectAttributes, Locale locale) {
        if (result.hasErrors()) {
            return "tipoDeRopa-form";
        }
        tipoDeRopaRepository.save(tipoDeRopa);
        redirectAttributes.addFlashAttribute("successMessage", messageSource.getMessage("msg.tipoDeRopa-controller.insert.success", null, locale));
        return "redirect:/tipos-de-ropa";
    }

    @PostMapping("/update")
    public String updateTipoDeRopa(@Valid @ModelAttribute("tipoDeRopa") TipoDeRopa tipoDeRopa, BindingResult result, RedirectAttributes redirectAttributes, Locale locale) {
        if (result.hasErrors()) {
            return "tipoDeRopa-form";
        }
        tipoDeRopaRepository.save(tipoDeRopa);
        redirectAttributes.addFlashAttribute("successMessage", messageSource.getMessage("msg.tipoDeRopa-controller.update.success", null, locale));
        return "redirect:/tipos-de-ropa";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete")
    public String deleteTipoDeRopa(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            tipoDeRopaRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Tipo de ropa eliminado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error al eliminar el tipo de ropa.");
        }
        return "redirect:/tipos-de-ropa";
    }
}
