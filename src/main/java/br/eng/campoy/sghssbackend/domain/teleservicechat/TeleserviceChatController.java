package br.eng.campoy.sghssbackend.domain.teleservicechat;

import br.eng.campoy.sghssbackend.domain.teleservicechat.dto.TeleserviceChatDto;
import br.eng.campoy.sghssbackend.domain.teleservicechat.dto.TeleserviceChatResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para gerenciamento do chat de teleatendimento.
 */
@RestController
@RequestMapping("/teleservice-chat")
@Tag(name = "Chat do Teleatendimento", description = "Gerenciamento de mensagens no teleatendimento entre profissionais e pacientes")
public class TeleserviceChatController {

    private final TeleserviceChatService teleserviceChatService;

    public TeleserviceChatController(TeleserviceChatService teleserviceChatService) {
        this.teleserviceChatService = teleserviceChatService;
    }

    /**
     * Envia uma mensagem no chat do teleatendimento.
     *
     * @param dto Dados da mensagem a ser enviada.
     * @return Mensagem enviada.
     */
    @PostMapping
    @Operation(
            summary = "Enviar mensagem no chat",
            description = "Envia uma mensagem no chat de um teleatendimento. Pode ser enviada por um paciente ou um profissional.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Mensagem enviada com sucesso",
                            content = @Content(schema = @Schema(implementation = TeleserviceChatResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    public ResponseEntity<TeleserviceChatResponseDto> sendMessage(@RequestBody TeleserviceChatDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(teleserviceChatService.sendMessage(dto));
    }

    /**
     * Lista todas as mensagens de um teleatendimento específico.
     *
     * @param teleserviceId ID do teleatendimento.
     * @return Lista de mensagens do teleatendimento.
     */
    @GetMapping("/{teleserviceId}")
    @Operation(
            summary = "Listar mensagens do chat",
            description = "Retorna todas as mensagens trocadas entre paciente e profissional em um teleatendimento específico.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de mensagens",
                            content = @Content(schema = @Schema(implementation = TeleserviceChatResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Teleatendimento não encontrado")
            }
    )
    public ResponseEntity<List<TeleserviceChatResponseDto>> listMessages(@PathVariable Long teleserviceId) {
        return ResponseEntity.ok(teleserviceChatService.listMessages(teleserviceId));
    }
}