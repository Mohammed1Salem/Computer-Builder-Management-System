package com.example.computerbuildermanagementsystem.Service;

import com.example.computerbuildermanagementsystem.Api.ApiException;
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
        List<PCBuild> pcBuilds = pcBuildRepository.findAll();
        if (pcBuilds.isEmpty()) throw new ApiException("No PCBuilds found");
        return pcBuilds;
    }

    public void add(Integer employeeId, PCBuild pcBuild) {
        if (!employeeRepository.getEmployeeById(employeeId).getRole().equalsIgnoreCase("ASSEMBLER"))
            throw new ApiException("Employee is not ASSEMBLER");

        Component cpu = componentRepository.getComponentById(pcBuild.getCpuId());
        if (cpu == null || !cpu.getType().equalsIgnoreCase("CPU")) throw new ApiException("CPU component not found");

        Component gpu = componentRepository.getComponentById(pcBuild.getGpuId());
        if (gpu == null || !gpu.getType().equalsIgnoreCase("GPU")) throw new ApiException("GPU component not found");

        Component ram = componentRepository.getComponentById(pcBuild.getRamId());
        if (ram == null || !ram.getType().equalsIgnoreCase("RAM")) throw new ApiException("RAM component not found");

        Component ssd = componentRepository.getComponentById(pcBuild.getSsdId());
        if (ssd == null || !ssd.getType().equalsIgnoreCase("SSD")) throw new ApiException("SSD component not found");

        pcBuild.setCreatedAt(LocalDateTime.now());
        pcBuild.setPrice(ram.getPrice() + ssd.getPrice() + gpu.getPrice() + cpu.getPrice() + 300);
        pcBuildRepository.save(pcBuild);
    }

    public void update(Integer employeeId, Integer pcBuildId, PCBuild pcBuild) {
        if (!employeeRepository.getEmployeeById(employeeId).getRole().equalsIgnoreCase("ASSEMBLER"))
            throw new ApiException("Employee is not ASSEMBLER");

        PCBuild old = pcBuildRepository.getPCBuildById(pcBuildId);
        if (old == null) throw new ApiException("PCBuild not found");

        Component cpu = componentRepository.getComponentById(pcBuild.getCpuId());
        if (cpu == null || !cpu.getType().equalsIgnoreCase("CPU")) throw new ApiException("CPU component not found");

        Component gpu = componentRepository.getComponentById(pcBuild.getGpuId());
        if (gpu == null || !gpu.getType().equalsIgnoreCase("GPU")) throw new ApiException("GPU component not found");

        Component ram = componentRepository.getComponentById(pcBuild.getRamId());
        if (ram == null || !ram.getType().equalsIgnoreCase("RAM")) throw new ApiException("RAM component not found");

        Component ssd = componentRepository.getComponentById(pcBuild.getSsdId());
        if (ssd == null || !ssd.getType().equalsIgnoreCase("SSD")) throw new ApiException("SSD component not found");

        old.setCpuId(pcBuild.getCpuId());
        old.setGpuId(pcBuild.getGpuId());
        old.setRamId(pcBuild.getRamId());
        old.setSsdId(pcBuild.getSsdId());
        old.setPrice(pcBuild.getPrice());

        pcBuildRepository.save(old);
    }

    public void delete(Integer employeeId, Integer pcBuildId) {
        if (!employeeRepository.getEmployeeById(employeeId).getRole().equalsIgnoreCase("ASSEMBLER"))
            throw new ApiException("Employee is not ASSEMBLER");

        PCBuild old = pcBuildRepository.getPCBuildById(pcBuildId);
        if (old == null) throw new ApiException("PCBuild not found");

        pcBuildRepository.delete(old);
    }

    public PCBuild getPCBuildById(Integer id) {
        PCBuild pcBuild = pcBuildRepository.getPCBuildById(id);
        if (pcBuild == null) throw new ApiException("PCBuild not found with id: " + id);
        return pcBuild;
    }
}
