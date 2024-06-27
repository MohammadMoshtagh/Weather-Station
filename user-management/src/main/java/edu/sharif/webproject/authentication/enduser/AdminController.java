package edu.sharif.webproject.authentication.enduser;

import edu.sharif.webproject.authentication.enduser.entity.dto.EndUsersResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
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
