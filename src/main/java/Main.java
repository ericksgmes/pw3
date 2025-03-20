import jakarta.persistence.EntityManager;
import model.Aluno;
import repository.AlunoDao;
import utils.JPAUtil;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import java.util.Date;
import java.util.UUID;

public class Main {

    private static EntityManager em;
    private static AlunoDao alunoDao;

    public static void main(String[] args) {
        em = JPAUtil.getEntityManager();
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
                    System.out.println("Encerrando o sistema.");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 6);

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
        System.out.println("6 - Fim");
        System.out.print("Digite a opção desejada: ");
    }

    private static void cadastrarAluno(Scanner scanner) {
        try {
            System.out.println("\n** CADASTRO DE ALUNO **");
            System.out.print("Digite o nome: ");
            String nome = scanner.nextLine();

            System.out.print("Digite o CPF: ");
            String cpf = scanner.nextLine();

            System.out.print("Digite a data de nascimento (dd/MM/yyyy): ");
            String dataNascimento = scanner.nextLine();

            System.out.print("Digite a nota 1: ");
            double nota1 = Double.parseDouble(scanner.nextLine());

            System.out.print("Digite a nota 2: ");
            double nota2 = Double.parseDouble(scanner.nextLine());

            System.out.print("Digite a nota 3: ");
            double nota3 = Double.parseDouble(scanner.nextLine());

            Aluno aluno = new Aluno(nome, cpf);
            aluno.setDataNascimento(dataNascimento);
            aluno.setNota1(nota1);
            aluno.setNota2(nota2);
            aluno.setNota3(nota3);
            // Exemplo: definir o aluno como aprovado se a média for maior ou igual a 7.0
            double media = (nota1 + nota2 + nota3) / 3.0;
            aluno.setAprovado(media >= 7.0);

            em.getTransaction().begin();
            alunoDao.cadastrar(aluno);
            em.getTransaction().commit();
            System.out.println("Aluno cadastrado com sucesso!");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("Erro ao cadastrar aluno: " + e.getMessage());
        }
    }

    private static void excluirAluno(Scanner scanner) {
        try {
            System.out.print("\nDigite o ID do aluno a ser excluído: ");
            String idStr = scanner.nextLine();
            UUID id = UUID.fromString(idStr);

            em.getTransaction().begin();
            Aluno aluno = alunoDao.buscarPorId(id);
            if (aluno != null) {
                alunoDao.remover(aluno);
                em.getTransaction().commit();
                System.out.println("Aluno removido com sucesso.");
            } else {
                em.getTransaction().rollback();
                System.out.println("Aluno não encontrado.");
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("Erro ao remover aluno: " + e.getMessage());
        }
    }

    private static void alterarAluno(Scanner scanner) {
        try {
            System.out.print("\nDigite o ID do aluno a ser alterado: ");
            String idStr = scanner.nextLine();
            UUID id = UUID.fromString(idStr);

            em.getTransaction().begin();
            Aluno aluno = alunoDao.buscarPorId(id);
            if (aluno == null) {
                System.out.println("Aluno não encontrado.");
                em.getTransaction().rollback();
                return;
            }

            System.out.print("Digite o novo nome (deixe em branco para manter): ");
            String novoNome = scanner.nextLine();
            if (!novoNome.trim().isEmpty()) {
                aluno.setNome(novoNome);
            }

            System.out.print("Digite o novo CPF (deixe em branco para manter): ");
            String novoCpf = scanner.nextLine();
            if (!novoCpf.trim().isEmpty()) {
                aluno.setCpf(novoCpf);
            }

            System.out.print("Digite a nova data de nascimento (dd/MM/yyyy) (deixe em branco para manter): ");
            String novaDataNascimento = scanner.nextLine();
            if (!novaDataNascimento.trim().isEmpty()) {
                aluno.setDataNascimento(novaDataNascimento);
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

            // Atualiza o status de aprovação com base nas notas
            double nota1 = aluno.getNota1();
            double nota2 = aluno.getNota2();
            double nota3 = aluno.getNota3();
            double media = (nota1 + nota2 + nota3) / 3.0;
            aluno.setAprovado(media >= 7.0);

            alunoDao.atualizar(aluno);
            em.getTransaction().commit();
            System.out.println("Aluno alterado com sucesso.");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("Erro ao alterar aluno: " + e.getMessage());
        }
    }

    private static void buscarAlunoPorNome(Scanner scanner) {
        try {
            System.out.print("\nDigite o nome ou parte do nome do aluno: ");
            String nome = scanner.nextLine();
            // É necessário que o AlunoDao tenha um método "buscarPorNome" que retorne uma lista de alunos.
            List<Aluno> alunos = alunoDao.buscarPorNome(nome);
            if (alunos.isEmpty()) {
                System.out.println("Nenhum aluno encontrado com esse nome.");
            } else {
                // Exibe os resultados em formato de tabela
                System.out.println("\n------------------------------------------------------------");
                System.out.printf("| %-36s | %-20s | %-11s |\n", "ID", "Nome", "CPF");
                System.out.println("------------------------------------------------------------");
                for (Aluno a : alunos) {
                    System.out.printf("| %-36s | %-20s | %-11s |\n",
                            a.getUuid(), a.getNome(), a.getCpf().getValue());
                }
                System.out.println("------------------------------------------------------------");
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
                System.out.println("\n------------------------------------------------------------");
                System.out.printf("| %-36s | %-20s | %-11s |\n", "ID", "Nome", "CPF");
                System.out.println("------------------------------------------------------------");
                for (Aluno a : alunosAprovados) {
                    System.out.printf("| %-36s | %-20s | %-11s |\n",
                            a.getUuid(), a.getNome(), a.getCpf().getValue());
                }
                System.out.println("------------------------------------------------------------");
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar alunos aprovados: " + e.getMessage());
        }
    }
}
