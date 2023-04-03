package com.notes.notes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NotesController {

	/*
	 * Common variable
	 */
	List<Notes> newNotes = new ArrayList<>();

	/*
	 * Home page
	 */
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("allnotes", newNotes);
		return "index";
	}

	/*
	 * Adding a new note page code
	 */
	@GetMapping("/addNotes")
	public String addNotes(@ModelAttribute("notes") Notes notes, Model model) {
		model.addAttribute("note", new Notes());
		return "newNote";
	}

	/*
	 * Adding/Updating a new note operation
	 */
	@PostMapping("/save")
	public String saveEmployee(@ModelAttribute("notes") Notes notes) {
		if (notes.getUpdateflag() != null && notes.getUpdateflag().equals("Y")) {
			// new code
			if (newNotes != null && !newNotes.isEmpty()) {
				for (Notes note : newNotes) {
					if (note.getId() == notes.getId()) {
						note.setNotes(notes.getNotes());

					}
				}

			} else {
				newNotes.add(notes);

			}

		} else {
			newNotes.add(notes);

		}

		return "redirect:/";
	}

	/*
	 * Updating a note page code
	 */
	@GetMapping("/showFormForUpdate/{id}")
	public String updateForm(@PathVariable(value = "id") long id, Model model) {
		Notes updateNote = new Notes();
		for (Notes note : newNotes) {
			if (note.getId() == id) {
				updateNote.setId(note.getId());
				updateNote.setNotes(note.getNotes());
				updateNote.setUpdateflag("Y");

			}
		}
		model.addAttribute("notes", updateNote);

		return "update";
	}

	/*
	 * Deleting a note
	 */
	@GetMapping("/deleteNote/{id}")
	public String deleteThroughId(@PathVariable(value = "id") long id) {
		if (newNotes != null && !newNotes.isEmpty()) {
			for (Notes note : newNotes) {
				if (note.getId() == id) {
					newNotes.remove(note);
					break;
				}

			}

		}
		return "redirect:/";

	}

}