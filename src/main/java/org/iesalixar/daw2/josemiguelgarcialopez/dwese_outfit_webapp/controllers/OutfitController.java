package org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_webapp.controllers;

import jakarta.validation.Valid;
import org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_webapp.entities.Outfit;
import org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_webapp.repositories.OutfitRepository;
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

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Controller
@RequestMapping("/outfits")
public class OutfitController {

    private static final Logger logger = LoggerFactory.getLogger(OutfitController.class);

    @Autowired
    private OutfitRepository outfitRepository;

    @Autowired
    private TipoDeRopaRepository tipoDeRopaRepository;

    @Autowired
    private MessageSource messageSource;

    /**
     * Lista todos los outfits y los pasa como atributo al modelo para que sean
     * accesibles en la vista `outfit.html`.
     *
     * @param model Objeto del modelo para pasar datos a la vista.
     * @return El nombre de la plantilla Thymeleaf para renderizar la lista de outfits.
     */
    @GetMapping
    public String listOutfits(Model model) {
        logger.info("Solicitando la lista de todos los outfits...");
        List<Outfit> listOutfits = null;
        try {
            listOutfits = outfitRepository.findAll();
            logger.info("Se han cargado {} outfits.", listOutfits.size());
        } catch (Exception e) {
            logger.error("Error al listar los outfits: {}", e.getMessage());
            model.addAttribute("errorMessage", "Error al listar los outfits.");
        }
        model.addAttribute("listOutfits", listOutfits);
        return "outfit";
    }

    /**
     * Muestra el formulario para crear un nuevo outfit.
     *
     * @param model Modelo para pasar datos a la vista.
     * @return El nombre de la plantilla Thymeleaf para el formulario.
     */
    @GetMapping("/new")
    public String showNewForm(Model model) {
        logger.info("Mostrando formulario para nuevo outfit.");
        List<TipoDeRopa> tiposDeRopa = tipoDeRopaRepository.findAll();
        model.addAttribute("outfit", new Outfit());
        model.addAttribute("tiposDeRopa", tiposDeRopa);
        return "outfit-form";
    }

    /**
     * Muestra el formulario para editar un outfit existente.
     *
     * @param id    ID del outfit a editar.
     * @param model Modelo para pasar datos a la vista.
     * @return El nombre de la plantilla Thymeleaf para el formulario.
     */
    @GetMapping("/edit")
    public String showEditForm(@RequestParam("id") Long id, Model model) {
        logger.info("Mostrando formulario de edición para el outfit con ID {}", id);
        Optional<Outfit> outfit = outfitRepository.findById(id);
        if (outfit.isEmpty()) {
            logger.warn("No se encontró el outfit con ID {}", id);
            model.addAttribute("errorMessage", "Outfit no encontrado.");
            return "redirect:/outfits";
        }
        List<TipoDeRopa> tiposDeRopa = tipoDeRopaRepository.findAll();
        model.addAttribute("outfit", outfit.get());
        model.addAttribute("tiposDeRopa", tiposDeRopa);
        return "outfit-form";
    }

    /**
     * Inserta un nuevo outfit en la base de datos.
     *
     * @param outfit              Objeto que contiene los datos del formulario.
     * @param redirectAttributes  Atributos para mensajes flash de redirección.
     * @return Redirección a la lista de outfits.
     */
    @PostMapping("/insert")
    public String insertOutfit(@Valid @ModelAttribute("outfit") Outfit outfit, BindingResult result, RedirectAttributes redirectAttributes, Locale locale) {
        logger.info("Insertando nuevo outfit con nombre {}", outfit.getNombre());
        if (result.hasErrors()) {
            return "outfit-form";
        }
        try {
            saveOutfit(outfit);
            logger.info("Outfit {} insertado con éxito.", outfit.getNombre());
        } catch (Exception e) {
            logger.error("Error al insertar el outfit {}: {}", outfit.getNombre(), e.getMessage());
            String errorMessage = messageSource.getMessage("msg.outfit-controller.insert.error", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
        }
        return "redirect:/outfits";
    }

    /**
     * Actualiza un outfit existente en la base de datos.
     *
     * @param outfit              Objeto que contiene los datos del formulario.
     * @param redirectAttributes  Atributos para mensajes flash de redirección.
     * @return Redirección a la lista de outfits.
     */
    @PostMapping("/update")
    public String updateOutfit(@Valid @ModelAttribute("outfit") Outfit outfit, BindingResult result, RedirectAttributes redirectAttributes, Locale locale) {
        logger.info("Actualizando outfit con ID {}", outfit.getId());
        if (result.hasErrors()) {
            return "outfit-form";
        }
        try {
            saveOutfit(outfit);
            logger.info("Outfit con ID {} actualizado con éxito.", outfit.getId());
        } catch (Exception e) {
            logger.error("Error al actualizar el outfit con ID {}: {}", outfit.getId(), e.getMessage());
            String errorMessage = messageSource.getMessage("msg.outfit-controller.update.error", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
        }
        return "redirect:/outfits";
    }

    /**
     * Elimina un outfit de la base de datos.
     *
     * @param id                 ID del outfit a eliminar.
     * @param redirectAttributes Atributos para mensajes flash de redirección.
     * @return Redirección a la lista de outfits.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete")
    public String deleteOutfit(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        logger.info("Eliminando outfit con ID {}", id);
        try {
            outfitRepository.deleteById(id);
            logger.info("Outfit con ID {} eliminado con éxito.", id);
        } catch (Exception e) {
            logger.error("Error al eliminar el outfit con ID {}: {}", id, e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Error al eliminar el outfit.");
        }
        return "redirect:/outfits";
    }

    /**
     * Método privado para guardar o actualizar el outfit.
     *
     * @param outfit Objeto Outfit a guardar o actualizar.
     */
    private void saveOutfit(Outfit outfit) {
        outfit.setFechaCreacion(new Date()); // Establecer la fecha de creación del outfit
        outfitRepository.save(outfit);
    }
}
