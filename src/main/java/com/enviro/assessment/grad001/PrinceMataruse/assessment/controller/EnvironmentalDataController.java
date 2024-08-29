package com.enviro.assessment.grad001.PrinceMataruse.assessment.controller;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.enviro.assessment.grad001.PrinceMataruse.assessment.model.EnvironmentalData;
import com.enviro.assessment.grad001.PrinceMataruse.assessment.repository.EnvironmentalDataRepository;

@Controller
public class EnvironmentalDataController {

    private static final Logger logger = LoggerFactory.getLogger(EnvironmentalDataController.class);

    @Autowired
    private EnvironmentalDataRepository repository;

    @GetMapping("/")
    public String index() {
        return "uploadForm";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/";
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String content = reader.lines().collect(Collectors.joining("\n"));

            // Validating file content (simple example: check for unwanted characters)
            if (content.contains("@")) {
                redirectAttributes.addFlashAttribute("message", "File contains invalid characters.");
                return "redirect:/";
            }

            // Showing the content to the user for confirmation after processing
            model.addAttribute("fileContent", content);
            return "confirmSave";

        } catch (Exception e) {
            logger.error("Error reading file: ", e);
            redirectAttributes.addFlashAttribute("message", "An error occurred while processing the file.");
            return "redirect:/";
        }
    }

    @PostMapping("/save")
    public String saveFileData(@RequestParam("fileContent") String fileContent, RedirectAttributes redirectAttributes) {
        EnvironmentalData data = new EnvironmentalData();
        data.setDataContent(fileContent);
        repository.save(data);
        redirectAttributes.addFlashAttribute("message", "File data saved successfully.");
        return "redirect:/";
    }




}



