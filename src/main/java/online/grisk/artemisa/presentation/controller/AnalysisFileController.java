package online.grisk.artemisa.presentation.controller;

import online.grisk.artemisa.bean.DataIntegrationBean;
import online.grisk.artemisa.bean.VariableBean;
import online.grisk.artemisa.domain.entity.DataIntegration;
import online.grisk.artemisa.domain.entity.Variable;
import online.grisk.artemisa.domain.service.DataIntegrationService;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class AnalysisFileController {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DataIntegrationService dataIntegrationService;

    @PostMapping("/v1/analysisFiles/organization/{organization-id}")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("organization-id") long idOrganization) {


        //retornar dataintegration con n variables


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

            List<VariableBean> lista = new ArrayList<>();

            for (Variable var : variables) {
                System.out.println(var.getCoordinate());
                Object valor = null;
                String tipo = "";
                try {
                    if (var.getTypeVariable().getCode().equalsIgnoreCase("ND")) {
                        valor = Double.parseDouble(getCell(workbook, var.getCoordinate()).toString());
                        tipo = "DOUBLE";
                    } else if (var.getTypeVariable().getCode().equalsIgnoreCase("NE")) {
                        valor = Integer.parseInt(getCell(workbook, var.getCoordinate()).toString());
                        tipo = "INT";
                    } else {// PA
                        valor = getCell(workbook, var.getCoordinate()).toString();
                        tipo = "STRING";
                    }
                } catch (Exception e) {
                    valor = var.getDefaultValue();
                    tipo = var.getTypeVariable().getName();
                }

                VariableBean varBean = new VariableBean();
                varBean.setIdVariable(var.getIdVariable());
                varBean.setName(var.getName());
                varBean.setCode(var.getCode());
                varBean.setCoordinate(var.getCoordinate());
                varBean.setDefaultValue(var.getDefaultValue());
                varBean.setTypeVariable(var.getTypeVariable());
                varBean.setBureau(var.isBureau());
                varBean.setValue(valor);
                varBean.setType(tipo);
                lista.add(varBean);

            }
            DataIntegrationBean response = new DataIntegrationBean();
            response.setIdDataIntegration(dataIntegration.getIdDataIntegration());
            response.setOrganization(dataIntegration.getIdDataIntegration());
            response.setCreatedAt(dataIntegration.getCreatedAt());
            response.setEnabled(dataIntegration.isEnabled());
            response.setBureau(dataIntegration.isBureau());
            response.setAnalyticsFileName(dataIntegration.getAnalyticsFileName());
            response.setAnalyticsFile(dataIntegration.getAnalyticsFile());
            response.setVariables(lista);
            return new ResponseEntity<DataIntegrationBean>(response, HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return new ResponseEntity<String>("ok", HttpStatus.OK);
    }

    public Cell getCell(Workbook workbook, String cellName) {
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


}
