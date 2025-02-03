package org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_webapp.controllers;

import jakarta.validation.Valid;
import org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_webapp.repositories.TipoDeRopaRepository;
import org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_webapp.entities.TipoDeRopa;
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

@Controller
@RequestMapping("/tipos-de-ropa")
public class TipoDeRopaController {

    private static final Logger logger = LoggerFactory.getLogger(TipoDeRopaController.class);

    @Autowired
    private TipoDeRopaRepository tipoDeRopaRepository;

    @Autowired
    private MessageSource messageSource;

    @GetMapping
    public String listTiposDeRopa(Model model) {
        logger.info("Solicitando la lista de todos los tipos de ropa...");
        try {
            List<TipoDeRopa> listTiposDeRopa = tipoDeRopaRepository.findAll();
            model.addAttribute("listTiposDeRopa", listTiposDeRopa);
            logger.info("Se han cargado {} tipos de ropa.", listTiposDeRopa.size());
        } catch (Exception e) {
            logger.error("Error al listar los tipos de ropa", e);
            model.addAttribute("errorMessage", "Error al listar los tipos de ropa.");
        }
        return "tipoDeRopa";
    }

    @GetMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public String showNewForm(Model model) {
        logger.info("Mostrando formulario para nuevo tipo de ropa.");
        model.addAttribute("tipoDeRopa", new TipoDeRopa());
        return "tipoDeRopa-form";
    }

    @GetMapping("/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String showEditForm(@RequestParam("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        logger.info("Mostrando formulario de edición para el tipo de ropa con ID {}", id);
        Optional<TipoDeRopa> tipoDeRopa = tipoDeRopaRepository.findById(id);
        if (tipoDeRopa.isEmpty()) {
            logger.warn("No se encontró el tipo de ropa con ID {}", id);
            redirectAttributes.addFlashAttribute("errorMessage", "Tipo de ropa no encontrado.");
            return "redirect:/tipos-de-ropa";
        }
        model.addAttribute("tipoDeRopa", tipoDeRopa.get());
        return "tipoDeRopa-form";
    }

    @PostMapping("/insert")
    @PreAuthorize("hasRole('ADMIN')")
    public String insertTipoDeRopa(@Valid @ModelAttribute("tipoDeRopa") TipoDeRopa tipoDeRopa, BindingResult result,
                                   RedirectAttributes redirectAttributes, Locale locale) {
        logger.info("Insertando nuevo tipo de ropa con nombre {}", tipoDeRopa.getNombre());
        if (result.hasErrors()) {
            return "tipoDeRopa-form";
        }
        try {
            tipoDeRopaRepository.save(tipoDeRopa);
            logger.info("Tipo de ropa con nombre {} insertado con éxito.", tipoDeRopa.getNombre());
        } catch (Exception e) {
            logger.error("Error al insertar el tipo de ropa", e);
            String errorMessage = messageSource.getMessage("msg.tipoDeRopa-controller.insert.error", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/tipos-de-ropa/new";
        }
        return "redirect:/tipos-de-ropa";
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateTipoDeRopa(@Valid @ModelAttribute("tipoDeRopa") TipoDeRopa tipoDeRopa, BindingResult result,
                                   RedirectAttributes redirectAttributes, Locale locale) {
        logger.info("Actualizando tipo de ropa con ID {}", tipoDeRopa.getId());
        if (result.hasErrors()) {
            return "tipoDeRopa-form";
        }
        try {
            if (!tipoDeRopaRepository.existsById(tipoDeRopa.getId())) {
                logger.warn("No se encontró el tipo de ropa con ID {}", tipoDeRopa.getId());
                redirectAttributes.addFlashAttribute("errorMessage", "Tipo de ropa no encontrado.");
                return "redirect:/tipos-de-ropa";
            }
            tipoDeRopaRepository.save(tipoDeRopa);
            logger.info("Tipo de ropa con ID {} actualizado con éxito.", tipoDeRopa.getId());
        } catch (Exception e) {
            logger.error("Error al actualizar el tipo de ropa", e);
            String errorMessage = messageSource.getMessage("msg.tipoDeRopa-controller.update.error", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/tipos-de-ropa/edit?id=" + tipoDeRopa.getId();
        }
        return "redirect:/tipos-de-ropa";
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteTipoDeRopa(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        logger.info("Eliminando tipo de ropa con ID {}", id);
        try {
            if (!tipoDeRopaRepository.existsById(id)) {
                logger.warn("No se encontró el tipo de ropa con ID {}", id);
                redirectAttributes.addFlashAttribute("errorMessage", "Tipo de ropa no encontrado.");
                return "redirect:/tipos-de-ropa";
            }
            tipoDeRopaRepository.deleteById(id);
            logger.info("Tipo de ropa con ID {} eliminado con éxito.", id);
        } catch (Exception e) {
            logger.error("Error al eliminar el tipo de ropa", e);
            redirectAttributes.addFlashAttribute("errorMessage", "Error al eliminar el tipo de ropa.");
        }
        return "redirect:/tipos-de-ropa";
    }
}
