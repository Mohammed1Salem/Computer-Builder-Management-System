package com.example.computerbuildermanagementsystem.Service;

import com.example.computerbuildermanagementsystem.Model.Component;
import com.example.computerbuildermanagementsystem.Model.PCBuild;
import com.example.computerbuildermanagementsystem.Repository.ComponentRepository;
import com.example.computerbuildermanagementsystem.Repository.EmployeeRepository;
import com.example.computerbuildermanagementsystem.Repository.PCBuildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PCBuildService {

    private final PCBuildRepository pcBuildRepository;
    private final ComponentRepository componentRepository;
    private final EmployeeRepository employeeRepository;

    public List<PCBuild> get() {
        return pcBuildRepository.findAll();
    }

    public String add(Integer employeeId, PCBuild pcBuild) {
        if (!employeeRepository.getEmployeeById(employeeId).getRole().equalsIgnoreCase("ASSEMBLER"))
            return "Employee is not ASSEMBLER";

        Component cpu = componentRepository.getComponentById(pcBuild.getCpuId());
        if (cpu == null || !cpu.getType().equalsIgnoreCase("CPU")) return "CPU component not found";

        Component gpu = componentRepository.getComponentById(pcBuild.getGpuId());
        if (gpu == null || !gpu.getType().equalsIgnoreCase("GPU")) return "GPU component not found";

        Component ram = componentRepository.getComponentById(pcBuild.getRamId());
        if (ram == null || !ram.getType().equalsIgnoreCase("RAM")) return "RAM component not found";

        Component ssd = componentRepository.getComponentById(pcBuild.getSsdId());
        if (ssd == null || !ssd.getType().equalsIgnoreCase("SSD")) return "SSD component not found";

        pcBuild.setCreatedAt(LocalDateTime.now());
        pcBuild.setPrice(ram.getPrice() + ssd.getPrice() + gpu.getPrice() + cpu.getPrice() + 300);
        pcBuildRepository.save(pcBuild);

        return "PCBuild added successfully";
    }

    public String update(Integer employeeId, Integer pcBuildId, PCBuild pcBuild) {

        if (!employeeRepository.getEmployeeById(employeeId).getRole().equalsIgnoreCase("ASSEMBLER"))
            return "Employee is not ASSEMBLER";

        PCBuild old = pcBuildRepository.getPCBuildById(pcBuildId);
        if (old == null) return "PCBuild not found";

        Component cpu = componentRepository.getComponentById(pcBuild.getCpuId());
        if (cpu == null || !cpu.getType().equalsIgnoreCase("CPU")) return "CPU component not found";

        Component gpu = componentRepository.getComponentById(pcBuild.getGpuId());
        if (gpu == null || !gpu.getType().equalsIgnoreCase("GPU")) return "GPU component not found";

        Component ram = componentRepository.getComponentById(pcBuild.getRamId());
        if (ram == null || !ram.getType().equalsIgnoreCase("RAM")) return "RAM component not found";

        Component ssd = componentRepository.getComponentById(pcBuild.getSsdId());
        if (ssd == null || !ssd.getType().equalsIgnoreCase("SSD")) return "SSD component not found";

        old.setCpuId(pcBuild.getCpuId());
        old.setGpuId(pcBuild.getGpuId());
        old.setRamId(pcBuild.getRamId());
        old.setSsdId(pcBuild.getSsdId());
        old.setPrice(pcBuild.getPrice());
        pcBuildRepository.save(old);

        return "PCBuild updated successfully";
    }

    public String delete(Integer employeeId, Integer pcBuildId) {
        if (!employeeRepository.getEmployeeById(employeeId).getRole().equalsIgnoreCase("ASSEMBLER"))
            return "Employee is not ASSEMBLER";

        PCBuild old = pcBuildRepository.getPCBuildById(pcBuildId);
        if (old == null) return "PCBuild not found";
        pcBuildRepository.delete(old);
        return "PCBuild delete successfully";
    }
    public PCBuild getPCBuildById(Integer id) {
        return pcBuildRepository.getPCBuildById(id);
    }

}
