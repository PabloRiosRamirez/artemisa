package online.grisk.artemisa.domain.service;

import online.grisk.artemisa.domain.entity.DataIntegration;
import online.grisk.artemisa.domain.entity.Microservice;
import online.grisk.artemisa.domain.entity.Variable;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class OrchestrationService {

    @Autowired
    Microservice microserviceAtenea;

    @Autowired
    DataIntegrationService dataIntegrationService;


    public List<Map> extractVariables(MultipartFile file, long idOrganization){
        Workbook workbook = null;
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        try {
            if (extension.equalsIgnoreCase("xlsx")) {
                workbook = new XSSFWorkbook(file.getInputStream());
            } else if (extension.equalsIgnoreCase("xls")) {
                workbook = new HSSFWorkbook(file.getInputStream());
            }
            DataIntegration dataIntegration = dataIntegrationService.findByOrganization(idOrganization);
            Collection<Variable> variables = dataIntegration.getVariableCollection();
            List<Map> listaVariables = new ArrayList<>();
            for (Variable var : variables) {
                Object value = null;
                String type = "";
                Boolean isValueDefault = false;
                try {
                    if (var.getTypeVariable().getCode().equalsIgnoreCase("ND")) {
                        value = Double.parseDouble(getCell(workbook, var.getCoordinate()).toString());
                        type = "ND";
                        isValueDefault = false;
                    } else if (var.getTypeVariable().getCode().equalsIgnoreCase("NE")) {
                        value = Integer.parseInt(getCell(workbook, var.getCoordinate()).toString());
                        type = "NE";
                        isValueDefault = false;
                    } else {// PA
                        value = getCell(workbook, var.getCoordinate()).toString();
                        type = "PA";
                        isValueDefault = false;
                    }
                } catch (Exception e) {
                    value = var.getDefaultValue();
                    type = var.getTypeVariable().getCode();
                    isValueDefault = true;
                }
                Map<String, Object> variable = new HashMap<>();
                variable.put("code", var.getName());
                variable.put("type", type);
                variable.put("value", value);
                variable.put("isValueDefault", isValueDefault);
                listaVariables.add(variable);
            }
            return listaVariables;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    private Cell getCell(Workbook workbook, String cellName) {
        Pattern r = Pattern.compile("^([A-Z]+)([0-9]+)$");
        Matcher m = r.matcher(cellName);
        if (m.matches()) {
            String columnName = m.group(1);
            int rowNumber = Integer.parseInt(m.group(2));
            if (rowNumber > 0) {
                return workbook.getSheetAt(0).getRow(rowNumber - 1).getCell(CellReference.convertColStringToIndex(columnName));
            }
        }
        return null;
    }

    public String extractRut(Map payload){
        return payload.get("rut").toString();
    }
}
