package com.superbpm.platform.controller.oauth2;

import com.superbpm.platform.entity.oauth2.Client;
import com.superbpm.platform.service.oauth2.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/oauth2/client")
public class ClientController {

  @Autowired
  private ClientService clientService;

  @RequestMapping(method = RequestMethod.GET)
  public String list(Model model) {
    model.addAttribute("clientList", clientService.findAll());
    return "oauth2/client/list";
  }

  @RequestMapping(value = "/create", method = RequestMethod.GET)
  public String showCreateForm(Model model) {
    model.addAttribute("client", new Client());
    model.addAttribute("op", "新增");
    model.addAttribute("action", "create");
    return "oauth2/client/edit";
  }

  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public String create(@ModelAttribute("form") Client client, RedirectAttributes redirectAttributes) {
    clientService.createClient(client);
    redirectAttributes.addFlashAttribute("msg", "新增成功");
    return "redirect:/oauth2/client";
  }

  @RequestMapping(value = "/{id}/update", method = RequestMethod.GET)
  public String showUpdateForm(@PathVariable("id") String id, Model model) {
    model.addAttribute("client", clientService.findOne(id));
    model.addAttribute("op", "修改");
    model.addAttribute("action", "update");
    return "oauth2/client/edit";
  }

  @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
  public String update(Client client, RedirectAttributes redirectAttributes) {
    clientService.updateClient(client);
    redirectAttributes.addFlashAttribute("msg", "修改成功");
    return "redirect:/oauth2/client";
  }

  @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
  public String showDeleteForm(@PathVariable("id") String id, Model model) {
    model.addAttribute("client", clientService.findOne(id));
    model.addAttribute("op", "删除");
    model.addAttribute("action", "delete");
    return "oauth2/client/edit";
  }

  @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
  public String delete(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
    clientService.deleteClient(id);
    redirectAttributes.addFlashAttribute("msg", "删除成功");
    return "redirect:/oauth2/client";
  }

}
