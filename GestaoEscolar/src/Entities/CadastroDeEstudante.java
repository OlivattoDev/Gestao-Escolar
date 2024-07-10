package Entities;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import Application.Estudante;

public class CadastroDeEstudante {

	private Path arquivo;

	public CadastroDeEstudante() {
		try {
			this.arquivo = Path.of("estudantes.csv");
			if (!Files.exists(arquivo)) {
				Files.createFile(arquivo);
			}
		} catch (Exception e) {
			throw new RuntimeException("Erro ao criar arquivo de estudantes!");
		}
	}

	public void cadastrar(Estudante estudante) {
		// validacoes
		if (estudante.getNome().isBlank()) {
			throw new IllegalArgumentException("Campo nome é obrigatório!");
		}

		if (estudante.getCpf().isBlank()) {
			throw new IllegalArgumentException("Campo cpf é obrigatório!");
		}

		if (estudante.getEmail().isBlank()) {
			throw new IllegalArgumentException("Campo email é obrigatório!");
		}

		if (estudante.getTelefone().isBlank()) {
			throw new IllegalArgumentException("Campo telefone é obrigatório!");
		}

		if (estudante.getEndereco().isBlank()) {
			throw new IllegalArgumentException("Campo endereço é obrigatório!");
		}

		var estudantesCadastrados = listar();
		for (var e : estudantesCadastrados) {
			if (e.getCpf().equals(estudante.getCpf())) {
				throw new IllegalArgumentException("Cpf já cadastrado!");
			}

			if (e.getEmail().equals(estudante.getEmail())) {
				throw new IllegalArgumentException("email já cadastrado!");
			}
		}

		try {
			Files.writeString(arquivo, estudante.getNome() + "," + estudante.getTelefone() + ","
					+ estudante.getEndereco() + "," + estudante.getCpf() + "," + estudante.getEmail() + "\n",
					StandardOpenOption.APPEND);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao cadastrar estudante no arquivo!");
		}
	}

	public List<Estudante> listar() {
		var lista = new ArrayList<Estudante>();

		try {
			var linhas = Files.readAllLines(arquivo);

			for (var linha : linhas) {
				var campos = linha.split(",");
				var estudante = new Estudante(campos[0], campos[1], campos[2], campos[3], campos[4]);
				lista.add(estudante);
			}

			return lista;
		} catch (Exception e) {
			throw new RuntimeException("Erro ao carregar estudantes do arquivo!");
		}
	}
}