package com.globaltours.agencia.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.globaltours.agencia.model.Cliente;
import com.globaltours.agencia.model.Comentario;
import com.globaltours.agencia.model.Reserva;
import com.globaltours.agencia.model.Viagem;
import com.globaltours.agencia.service.ClienteService;
import com.globaltours.agencia.service.ComentarioService;
import com.globaltours.agencia.service.ReservaService;
import com.globaltours.agencia.service.ViagemService;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    
    private final ReservaService reservaService;
    private final ClienteService clienteService;
    private final ViagemService viagemService;
    private final ComentarioService comentarioService;

    public ClienteController(ReservaService reservaService, ClienteService clienteService, ViagemService viagemService, ComentarioService comentarioService) {
        this.reservaService = reservaService;
        this.clienteService = clienteService;
        this.viagemService = viagemService;
        this.comentarioService = comentarioService;
    }

    @GetMapping("/listar")
    public List<Cliente> listarClientes() {
        return clienteService.listarClientes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarClientePorId(@PathVariable Long id) {
        try {
            Optional<Cliente> cliente = clienteService.buscarCliente(id);
            // CASO O CLIENTE EXISTA, RETORNA O STATUS 200 + O CLIENTE
            return cliente.isPresent() ? ResponseEntity.ok(cliente.get()) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            // CASO OCORRA ALGUM ERRO, RETORNA O STATUS 400 + MENSAGEM DE ERRO
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/criar-cliente")
    public ResponseEntity<?> criarCliente(@RequestBody Cliente cliente) {
        try {
            Cliente novoCliente = clienteService.salvarCliente(cliente);
            // CASO O CLIENTE SEJA CRIADO COM SUCESSO, RETORNA O STATUS 201 + O CLIENTE CRIADO
            return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);
        } catch (Exception e) {
            // CASO OCORRA ALGUM ERRO, RETORNA O STATUS 500 + MENSAGEM DE ERRO
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/atualizar-cliente")
    public ResponseEntity<Cliente> atualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {

        Optional<Cliente> clienteEncontrado = clienteService.buscarCliente(id);
        if (clienteEncontrado.isPresent()) {
            return ResponseEntity.ok(clienteService.atualizarCliente(id, cliente));
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarCliente(@PathVariable Long id) {
        try {
            clienteService.deletarCliente(id);
            // CASO O CLIENTE SEJA DELETADO COM SUCESSO, RETORNA O STATUS 200
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            // CASO OCORRA ALGUM ERRO, RETORNA O STATUS 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ENDPOINTS DE COMENTÁRIOS

    // @GetMapping("/{id}/comentarios")
    // public ResponseEntity<?> listarComentarios(@PathVariable Long id, @RequestParam Long viagemID) {

    //     Optional<Cliente> cliente = clienteService.buscarCliente(id);
    //     if (cliente.isPresent()) {
    //         if (viagemID != null) {
    //             Optional<Viagem> viagem = viagemService.buscarViagem(viagemID);
    //             if (viagem.isPresent()) {

    //                 return ResponseEntity.ok();
    //             } else {
    //                 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Viagem não encontrada");
    //             }
    //         } else {
    //             return ResponseEntity.ok(cliente.get().getComentarios());
    //         }
    //     } else {
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado");
    //     }

    // }

    @PostMapping("/comentar/{viagemID}")
    public ResponseEntity<?> adicionarComentario(@PathVariable Long viagemID, @RequestBody Comentario comentario, @RequestParam Long clienteID) {

        Optional<Cliente> cliente = clienteService.buscarCliente(clienteID);
        Optional<Viagem> viagem = viagemService.buscarViagem(viagemID);

        if (cliente.isPresent() && viagem.isPresent()) {
            comentario.setCliente(cliente.get());
            comentario.setViagem(viagem.get());
            System.out.println("Comentário: " + comentario);
            comentarioService.salvarComentario(comentario);
            return ResponseEntity.status(HttpStatus.CREATED).body(comentario);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    // ENDPOINTS DE RESERVAS

    @GetMapping("/{id}/reservas")
    public ResponseEntity<?> listarReservas(@PathVariable Long id) {
        try {
            Optional<Cliente> cliente = clienteService.buscarCliente(id);
            if (cliente.isPresent()) {
                List<Reserva> reservas = reservaService.listarReservasPorCliente(id);
                return ResponseEntity.status(HttpStatus.OK).body(reservas);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/adicionar-reserva/{clienteId}")
    public ResponseEntity<?> adicionarReserva(@PathVariable Long clienteId, @RequestParam Long viagemId, @RequestBody Reserva reserva) {
        try {
            Optional<Cliente> cliente = clienteService.buscarCliente(clienteId);
            Optional<Viagem> viagem = viagemService.buscarViagem(viagemId);

            if (cliente.isPresent() && viagem.isPresent()) {
                reserva.setCliente(cliente.get());
                reserva.setViagem(viagem.get());
                reserva.setDataReserva(reserva.getDataReserva());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            Reserva novaReserva = reservaService.criarReserva(reserva);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaReserva);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


}
