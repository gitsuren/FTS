package com.suru.fts.controller;

import static com.suru.fts.routes.Routes.SYSTEM_CREATE_URI;
import static com.suru.fts.routes.Routes.SYSTEM_DELETE_URI;
import static com.suru.fts.routes.Routes.SYSTEM_NAME_EDIT_URI;
import static com.suru.fts.routes.Routes.SYSTEM_URI;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.suru.fts.api.exception.NoSuchMethodException;
import com.suru.fts.api.exception.ResourceNotFoundException;
import com.suru.fts.dto.ToggleSystemFormBean;
import com.suru.fts.mongo.domain.ToggleSystem;
import com.suru.fts.model.ViewModelBuilder;
import com.suru.fts.routes.Routes;
import com.suru.fts.service.ToggleService;


@Controller
public class SystemController extends BaseController {

	@Autowired
	private ToggleService toggleService;

	@Autowired
	private ViewModelBuilder modelBuilder;


	@RequestMapping(value = Routes.TOGGLE_FEATURES_SYSTEM_URI, method = RequestMethod.GET)
	public ModelAndView listSystems() {
		List<ToggleSystem> systems = toggleService.getActiveSystems();
		return modelBuilder.buildSystemsModel(systems);
	}


	@RequestMapping(value = SYSTEM_URI, method = RequestMethod.GET)
	public ModelAndView getSystem(@PathVariable final String systemName) {

		ToggleSystem system = toggleService.getSystem(systemName);
		if (system == null) {
			throw new ResourceNotFoundException(systemName + " is not found");
		}

		return modelBuilder.buildSystemModel(system);
	}


	@RequestMapping(value = SYSTEM_CREATE_URI, method = RequestMethod.GET)
	public ModelAndView beginCreate() {
		return modelBuilder.buildSystemCreateModel(false, null);
	}


	@RequestMapping(value = SYSTEM_CREATE_URI, method = RequestMethod.POST)
	public ModelAndView addSystem(@ModelAttribute final ToggleSystemFormBean system, final HttpServletRequest request) {

		ToggleSystem newSystem = toggleService.createSystem(system, getUserId(request));
		return new ModelAndView("redirect:/admin/system/" + newSystem.getSystemName());
	}


	@RequestMapping(value = SYSTEM_NAME_EDIT_URI, method = RequestMethod.GET)
	public ModelAndView editSystem(@PathVariable(value = "systemName") final String systemName) {
		ModelAndView editMV = modelBuilder.buildSystemCreateModel(true, systemName);
		ToggleSystem system = toggleService.getSystem(systemName);
		if (system == null) {
			throw new ResourceNotFoundException(systemName + " is not found");
		}
		editMV.addObject("systemName", system.getSystemName());
		editMV.addObject("systemDescription", system.getDescription());
		editMV.addObject("isEdit", true);
		return editMV;
	}


	@RequestMapping(value = SYSTEM_NAME_EDIT_URI, method = RequestMethod.POST)
	public ModelAndView updateSystem(@ModelAttribute final ToggleSystemFormBean systemForm, final HttpServletRequest request) {

		toggleService.updateSystem(systemForm, getUserId(request));
		return new ModelAndView("redirect:/admin/system/" + systemForm.getSystemName());
	}

	@RequestMapping(value = SYSTEM_DELETE_URI, method = POST)
	public ModelAndView deleteSystemPostAction(@RequestParam final String systemName, @RequestParam final String action) {

		if (action.equals("delete")) {
			ToggleSystem system = toggleService.getSystem(systemName);
			if (system != null) {
				toggleService.deleteSystem(system);
				return redirect(Routes.TOGGLE_SYSTEM_HOME_URI);
			} else {
				throw new ResourceNotFoundException(systemName + " is not found");
			}
		} else if (action.equals("cancel")) {
			return redirect(Routes.TOGGLE_SYSTEM_HOME_URI);
		}
		throw new NoSuchMethodException(action + " is not a valid method");
	}


	@RequestMapping(value = Routes.SYSTEM_NAME_DELETE_URI, method = GET)
	public ModelAndView showDeleteConfirmation(@PathVariable final String systemName, final HttpServletRequest request) {
		return modelBuilder.buildDeleteConfirmationView(systemName, "cancel-link", Routes.TOGGLE_FEATURES_SYSTEM_URI);
	}


	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	public String handleResourceNotFoundException(final ResourceNotFoundException ex) {
		return ex.getMessage();
	}

	@ExceptionHandler(NoSuchMethodException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public String handleNoSuchMethodError(final NoSuchMethodException ex) {
		return ex.getMessage();
	}

}
