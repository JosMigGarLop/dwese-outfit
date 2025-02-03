package org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_webapp.controllers;

import jakarta.validation.Valid;
import org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_webapp.entities.Outfit;
import org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_webapp.entities.TipoDeRopa;
import org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_webapp.repositories.OutfitRepository;
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

    // Mostrar la lista de outfits
    @GetMapping
    public String listOutfits(Model model) {
        logger.info("Solicitando la lista de todos los outfits...");
        List<Outfit> listOutfits = outfitRepository.findAll();
        model.addAttribute("listOutfits", listOutfits);
        return "outfit";
    }

    // Mostrar formulario para nuevo outfit
    @GetMapping("/new")
    public String showNewForm(Model model) {
        logger.info("Mostrando formulario para nuevo outfit.");
        List<TipoDeRopa> tiposDeRopa = tipoDeRopaRepository.findAll();  // Obtener todos los tipos de ropa
        model.addAttribute("outfit", new Outfit());
        model.addAttribute("tiposDeRopa", tiposDeRopa);  // Pasar la lista de tipos de ropa al modelo
        return "outfit-form";
    }

    // Mostrar formulario para editar un outfit
    @GetMapping("/edit")
    public String showEditForm(@RequestParam("id") Long id, Model model) {
        logger.info("Mostrando formulario de edición para el outfit con ID {}", id);
        Optional<Outfit> outfit = outfitRepository.findById(id);
        if (outfit.isPresent()) {
            List<TipoDeRopa> tiposDeRopa = tipoDeRopaRepository.findAll();  // Obtener todos los tipos de ropa
            model.addAttribute("outfit", outfit.get());
            model.addAttribute("tiposDeRopa", tiposDeRopa);  // Pasar la lista de tipos de ropa al modelo
            return "outfit-form";
        } else {
            logger.warn("No se encontró el outfit con ID {}", id);
            return "redirect:/outfits";
        }
    }

    // Insertar un nuevo outfit
    @PostMapping("/insert")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public String insertOutfit(@Valid @ModelAttribute("outfit") Outfit outfit, BindingResult result,
                               RedirectAttributes redirectAttributes, Locale locale) {
        logger.info("Insertando nuevo outfit: {}", outfit.getNombre());
        if (result.hasErrors()) {
            return "outfit-form";
        }
        outfitRepository.save(outfit);
        logger.info("Outfit {} insertado con éxito.", outfit.getNombre());
        return "redirect:/outfits";
    }

    // Actualizar un outfit existente
    @PostMapping("/update")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public String updateOutfit(@Valid @ModelAttribute("outfit") Outfit outfit, BindingResult result,
                               RedirectAttributes redirectAttributes, Locale locale) {
        logger.info("Actualizando outfit con ID {}", outfit.getId());
        if (result.hasErrors()) {
            return "outfit-form";
        }
        outfitRepository.save(outfit);
        logger.info("Outfit con ID {} actualizado con éxito.", outfit.getId());
        return "redirect:/outfits";
    }

    // Eliminar un outfit
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
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
}