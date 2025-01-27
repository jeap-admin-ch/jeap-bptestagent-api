package ch.admin.bit.jeap.testagent.api;

import ch.admin.bit.jeap.testagent.api.act.ActionDto;
import ch.admin.bit.jeap.testagent.api.act.ActionResultDto;
import ch.admin.bit.jeap.testagent.api.prepare.PreparationDto;
import ch.admin.bit.jeap.testagent.api.prepare.PreparationResultDto;
import ch.admin.bit.jeap.testagent.api.update.DynamicDataDto;
import ch.admin.bit.jeap.testagent.api.verify.ReportDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "TestAgent API", description = """
        Every TestAgent/Simulator implements this API. \
        The Orchestrator uses this API to orchestrate the Business Process Tests\
        """)
@RequestMapping(value = "/api/tests")
public interface TestAgentOperations {

    @Operation(summary = "Prepare")
    @PutMapping("/{testId}")
    ResponseEntity<PreparationResultDto> prepare(@PathVariable String testId, @RequestBody PreparationDto preparationDto);

    @Operation(summary = "Update")
    @PutMapping("/{testId}/dynamicdata")
    void update(@PathVariable String testId, @RequestBody DynamicDataDto dynamicDataDto);

    @Operation(summary = "Act")
    @PostMapping("/{testId}/actions")
    ResponseEntity<ActionResultDto> act(@PathVariable String testId, @RequestBody ActionDto actionDto);

    @Operation(summary = "Verify")
    @GetMapping("/{testId}/report")
    ResponseEntity<ReportDto> verify(@PathVariable String testId);

    @Operation(summary = "Clean up")
    @DeleteMapping("/{testId}")
    void cleanUp(@PathVariable String testId);

}
