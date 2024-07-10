package Application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import Entities.CadastroDeCurso;
import Entities.CadastroDeEstudante;
import Entities.CadastroDeTurma;

public class Principal {

	public static void main(String[] args) {

		var leitor = new Scanner(System.in);
		var opcaoDigitada = 0;

		while (opcaoDigitada != 7) {
			exibirMenu();
			opcaoDigitada = Integer.parseInt(leitor.nextLine());
			if (opcaoDigitada == 1) {
				cadastrarTurma(leitor);
			} else if (opcaoDigitada == 2) {
				cadastrarEstudante(leitor);
			} else if (opcaoDigitada == 3) {
				listarTurmas();
			} else if (opcaoDigitada == 4) {
				listarEstudantes();
			} else if (opcaoDigitada == 5) {
				cadastrarCurso(leitor);
			} else if (opcaoDigitada == 6) {
				listarCursos();
			}
		}
	}

	private static void exibirMenu() {
		System.out.println("Menu principal:");
		System.out.println("1 - Cadastrar turma");
		System.out.println("2 - Cadastrar estudante");
		System.out.println("3 - Listar turmas");
		System.out.println("4 - Listar estudantes");
		System.out.println("5 - Cadastrar curso");
		System.out.println("6 - Listar cursos");
		System.out.println("7 - Sair");
		System.out.println("Digite o código da opção desejada:");
	}

	private static void listarEstudantes() {
		var cadastro = new CadastroDeEstudante();
		var estudantesCadastrados = cadastro.listar();
		for (var estudante : estudantesCadastrados) {
			System.out.println(estudante.getNome() + " - " + estudante.getTelefone() + " - " + estudante.getEndereco()
					+ " - " + estudante.getCpf() + " - " + estudante.getEmail());
		}
	}

	private static void listarCursos() {
		var cadastro = new CadastroDeCurso();
		var cursosCadastrados = cadastro.listar();
		for (var curso : cursosCadastrados) {
			System.out.println(curso.getCodigo() + " - " + curso.getNome() + " - " + curso.getCargaHoraria() + " - "
					+ curso.getNivel().getNome());
		}
	}

	private static void listarTurmas() {
		var cadastro = new CadastroDeTurma();
		var turmasCadastradas = cadastro.listar();
		var mascaraData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		for (var turma : turmasCadastradas) {
			System.out.println(turma.getCodigo() + " - " + turma.getCurso().getNome() + " - "
					+ turma.getDataInicio().format(mascaraData) + " - " + turma.getDataFim().format(mascaraData) + " - "
					+ turma.getPeriodo());
		}
	}

	private static void cadastrarEstudante(Scanner leitor) {
		System.out.println("Digite o nome do estudante:");
		var nome = leitor.nextLine();
		System.out.println("Digite o telefone do estudante:");
		var telefone = leitor.nextLine();
		System.out.println("Digite o endereco do estudante:");
		var endereco = leitor.nextLine();
		System.out.println("Digite o cpf do estudante:");
		var cpf = leitor.nextLine();
		System.out.println("Digite o email do estudante:");
		var email = leitor.nextLine();

		var estudante = new Estudante(nome, telefone, endereco, cpf, email);

		var cadastro = new CadastroDeEstudante();
		try {
			cadastro.cadastrar(estudante);
		} catch (IllegalArgumentException e) {
			System.out.println("Erro ao cadastrar estudante: " + e.getMessage());
		}
	}

	private static void cadastrarCurso(Scanner leitor) {
		System.out.println("Digite o código do curso:");
		var codigo = leitor.nextLine();
		System.out.println("Digite o nome do curso:");
		var nome = leitor.nextLine();
		System.out.println("Digite a carga horária (em horas) do curso:");
		var cargaHoraria = Integer.parseInt(leitor.nextLine());
		System.out.println("Digite o nível do curso (BASICO, INTERMEDIARIO, AVANCADO):");
		var nivelEmString = leitor.nextLine();

		var nivel = Nivel.valueOf(nivelEmString);

		var curso = new Curso(codigo, nome, cargaHoraria, nivel);

		var cadastro = new CadastroDeCurso();
		try {
			cadastro.cadastrar(curso);
		} catch (IllegalArgumentException e) {
			System.out.println("Erro ao cadastrar curso: " + e.getMessage());
		}
	}

	private static void cadastrarTurma(Scanner leitor) {
		System.out.println("Digite o código da turma:");
		var codigo = leitor.nextLine();

		System.out.println("Digite a data de início da turma (dd/MM/yyyy):");
		var dataInicio = LocalDate.parse(leitor.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

		System.out.println("Digite a data de fim da turma (dd/MM/yyyy):");
		var dataFim = LocalDate.parse(leitor.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

		System.out.println("Digite o período da turma (MATUTINO, VESPERTINO, NOTURNO, SABADOS):");
		var periodo = Periodo.valueOf(leitor.nextLine());

		System.out.println("Digite o código do curso:");
		var codigoCurso = leitor.nextLine();

		var cadastro = new CadastroDeTurma();
		try {
			cadastro.cadastrar(codigo, dataInicio, dataFim, periodo, codigoCurso);
		} catch (IllegalArgumentException e) {
			System.out.println("Erro ao cadastrar turma: " + e.getMessage());
		}
	}
}