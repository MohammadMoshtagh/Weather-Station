package edu.sharif.webproject.enduser;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PutMapping("/users")
    public boolean changeUserState(@RequestParam String username, @RequestParam boolean active) {
        return adminService.changeUserState(username, active);
    }

    @GetMapping("/users")
    public EndUsersResponse getAllUsers(@RequestParam int pageNum, @RequestParam int pageSize) {
        return adminService.getAllUsers(pageNum, pageSize);
    }
}
