import jakarta.persistence.EntityManager;
import model.Aluno;
import repository.AlunoDao;
import utils.JPAUtil;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Main {

    private static AlunoDao alunoDao;

    public static void main(String[] args) {
        EntityManager em = JPAUtil.getEntityManager();
        alunoDao = new AlunoDao(em);
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;

        do {
            exibirMenu();
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida! Tente novamente.");
                continue;
            }

            switch (opcao) {
                case 1:
                    cadastrarAluno(scanner);
                    break;
                case 2:
                    excluirAluno(scanner);
                    break;
                case 3:
                    alterarAluno(scanner);
                    break;
                case 4:
                    buscarAlunoPorNome(scanner);
                    break;
                case 5:
                    listarAlunosAprovados();
                    break;
                case 6:
                    listarTodosAlunos();
                    break;
                case 7:
                    System.out.println("Encerrando o sistema.");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 7);

        em.close();
        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n** CADASTRO DE ALUNOS **");
        System.out.println("1 - Cadastrar aluno");
        System.out.println("2 - Excluir aluno");
        System.out.println("3 - Alterar aluno");
        System.out.println("4 - Buscar aluno pelo nome");
        System.out.println("5 - Listar alunos aprovados");
        System.out.println("6 - Listar todos os alunos");
        System.out.println("7 - Fim");
        System.out.print("Digite a opção desejada: ");
    }

    private static void cadastrarAluno(Scanner scanner) {
        try {
            System.out.println("\n** CADASTRO DE ALUNO **");
            System.out.print("Digite o nome: ");
            String nome = scanner.nextLine();

            System.out.print("Digite o RA: ");
            String ra = scanner.nextLine();

            System.out.print("Digite o email: ");
            String email = scanner.nextLine();

            System.out.print("Digite a nota 1: ");
            double nota1 = Double.parseDouble(scanner.nextLine());

            System.out.print("Digite a nota 2: ");
            double nota2 = Double.parseDouble(scanner.nextLine());

            System.out.print("Digite a nota 3: ");
            double nota3 = Double.parseDouble(scanner.nextLine());

            Aluno aluno = new Aluno(nome, ra, email);
            aluno.setNota1(nota1);
            aluno.setNota2(nota2);
            aluno.setNota3(nota3);

            double media = (nota1 + nota2 + nota3) / 3.0;
            aluno.setAprovado(media >= 6.0);

            alunoDao.cadastrar(aluno);
            System.out.println("Aluno cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar aluno: " + e.getMessage());
        }
    }

    private static void excluirAluno(Scanner scanner) {
        try {
            Aluno a = buscarAlunoPorSelecao(scanner);
            if (a != null) {
                Aluno aluno = alunoDao.buscarPorId(a.getUuid());
                alunoDao.remover(aluno);
                System.out.println("Aluno removido com sucesso.");
            } else {
                System.out.println("Aluno não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao remover aluno: " + e.getMessage());
        }
    }

    private static void alterarAluno(Scanner scanner) {
        try {
            Aluno a  = buscarAlunoPorSelecao(scanner);
            if (a == null) {
                System.out.println("Aluno não encontrado.");
                return;
            }

            Aluno aluno = alunoDao.buscarPorId(a.getUuid());

            if (aluno == null) {
                System.out.println("Aluno não encontrado.");
                return;
            }

            System.out.print("Digite o novo nome (deixe em branco para manter): ");
            String novoNome = scanner.nextLine();
            if (!novoNome.trim().isEmpty()) {
                aluno.setNome(novoNome);
            }

            System.out.print("Digite o novo RA (deixe em branco para manter): ");
            String novoRa = scanner.nextLine();
            if (!novoRa.trim().isEmpty()) {
                aluno.setRa(novoRa);
            }

            System.out.print("Digite o novo email (deixe em branco para manter): ");
            String novoEmail = scanner.nextLine();
            if (!novoEmail.trim().isEmpty()) {
                aluno.setEmail(novoEmail);
            }

            System.out.print("Digite a nova nota 1 (deixe em branco para manter): ");
            String nota1Str = scanner.nextLine();
            if (!nota1Str.trim().isEmpty()) {
                double nota1 = Double.parseDouble(nota1Str);
                aluno.setNota1(nota1);
            }

            System.out.print("Digite a nova nota 2 (deixe em branco para manter): ");
            String nota2Str = scanner.nextLine();
            if (!nota2Str.trim().isEmpty()) {
                double nota2 = Double.parseDouble(nota2Str);
                aluno.setNota2(nota2);
            }

            System.out.print("Digite a nova nota 3 (deixe em branco para manter): ");
            String nota3Str = scanner.nextLine();
            if (!nota3Str.trim().isEmpty()) {
                double nota3 = Double.parseDouble(nota3Str);
                aluno.setNota3(nota3);
            }

            double nota1 = aluno.getNota1();
            double nota2 = aluno.getNota2();
            double nota3 = aluno.getNota3();
            double media = (nota1 + nota2 + nota3) / 3.0;
            aluno.setAprovado(media >= 6.0);

            alunoDao.atualizar(aluno);
            System.out.println("Aluno alterado com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro ao alterar aluno: " + e.getMessage());
        }
    }

    private static void buscarAlunoPorNome(Scanner scanner) {
        try {
            System.out.print("\nDigite o nome ou parte do nome do aluno: ");
            String nome = scanner.nextLine();
            List<Aluno> alunos = alunoDao.buscarPorNome(nome);
            if (alunos.isEmpty()) {
                System.out.println("Nenhum aluno encontrado com esse nome.");
            } else {
                printAlunosTable(alunos);
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar aluno: " + e.getMessage());
        }
    }

    private static void listarAlunosAprovados() {
        try {
            List<Aluno> alunosAprovados = alunoDao.listarAlunosAprovados();
            if (alunosAprovados.isEmpty()) {
                System.out.println("Nenhum aluno aprovado encontrado.");
            } else {
                printAlunosTable(alunosAprovados);
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar alunos aprovados: " + e.getMessage());
        }
    }

    private static void listarTodosAlunos() {
        try {
            List<Aluno> alunos = alunoDao.listarAlunos();
            if (alunos.isEmpty()) {
                System.out.println("Nenhum aluno encontrado.");
            } else {
                printAlunosTable(alunos);
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar todos os alunos: " + e.getMessage());
        }
    }

    private static Aluno buscarAlunoPorSelecao(Scanner scanner) {
        UUID idSelecionado = selecionarAlunoPorIndice(scanner);
        if (idSelecionado != null) {
            return alunoDao.buscarPorId(idSelecionado);
        }
        return null;
    }

    private static UUID selecionarAlunoPorIndice(Scanner scanner) {
        List<Aluno> alunos = alunoDao.listarAlunos();
        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno encontrado.");
            return null;
        }

        printAlunosTable(alunos);

        System.out.print("Digite o número do aluno desejado: ");
        try {
            int indice = Integer.parseInt(scanner.nextLine());
            if (indice < 0 || indice >= alunos.size()) {
                System.out.println("Índice inválido.");
                return null;
            }
            return alunos.get(indice).getUuid();
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
            return null;
        }
    }

    private static void printAlunosTable(List<Aluno> alunos) {
        int idWidth = "ID".length();
        int nomeWidth = "Nome".length();
        int raWidth = "RA".length();
        int emailWidth = "Email".length();

        for (Aluno a : alunos) {
            String idStr = a.getUuid().toString();
            String nomeStr = a.getNome().toString();
            String raStr = a.getRa();
            String emailStr = a.getEmail();
            if (idStr.length() > idWidth) idWidth = idStr.length();
            if (nomeStr.length() > nomeWidth) nomeWidth = nomeStr.length();
            if (raStr.length() > raWidth) raWidth = raStr.length();
            if (emailStr.length() > emailWidth) emailWidth = emailStr.length();
        }

        String format = "| %-" + idWidth + "s | %-" + nomeWidth + "s | %-" + raWidth + "s | %-" + emailWidth + "s |\n";
        String separator = "+"
                + "-".repeat(idWidth + 2) + "+"
                + "-".repeat(nomeWidth + 2) + "+"
                + "-".repeat(raWidth + 2) + "+"
                + "-".repeat(emailWidth + 2) + "+";

        System.out.println(separator);
        System.out.printf(format, "ID", "Nome", "RA", "Email");
        System.out.println(separator);
        for (Aluno a : alunos) {
            System.out.printf(format, a.getUuid().toString(), a.getNome().toString(), a.getRa(), a.getEmail());
        }
        System.out.println(separator);
    }
}
