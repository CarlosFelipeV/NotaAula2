import java.util.Scanner;

abstract class Conta {
    protected String titular;
    protected double saldo;

    public Conta(String titular) {
        this.titular = titular;
        this.saldo = 0;
    }

    public abstract void exibirDados();

    public void depositar(double valor) {
        if (valor > 0) {
            saldo += valor;
            System.out.println("Depósito realizado com sucesso! Novo saldo: R$" + saldo);
        } else {
            System.out.println("Valor de depósito inválido.");
        }
    }

    public void sacar(double valor) {
        if (valor > 0 && valor <= saldo) {
            saldo -= valor;
            System.out.println("Saque realizado com sucesso! Novo saldo: R$" + saldo);
        } else {
            System.out.println("Saldo insuficiente ou valor inválido.");
        }
    }

    public double getSaldo() {
        return saldo;
    }
}

class ContaCorrente extends Conta {
    private static final double CHEQUE_ESPECIAL = 1000.0;

    public ContaCorrente(String titular) {
        super(titular);
    }

    public void usarChequeEspecial(double valor) {
        if (valor <= saldo + CHEQUE_ESPECIAL) {
            saldo -= valor;
            System.out.println("Saque com cheque especial realizado com sucesso! Novo saldo: R$" + saldo);
        } else {
            System.out.println("Valor excede limite do cheque especial.");
        }
    }

    @Override
    public void exibirDados() {
        System.out.println("=== Conta Corrente ===");
        System.out.println("Titular: " + titular);
        System.out.println("Saldo: R$" + saldo);
    }
}

class ContaPoupanca extends Conta {
    public ContaPoupanca(String titular) {
        super(titular);
    }

    public void calcularRendimento(double selic) {
        double rendimento;
        if (selic > 8.5) {
            rendimento = 0.005 * saldo;
        } else {
            rendimento = 0.007 * selic * saldo;
        }
        saldo += rendimento;
        System.out.println("Rendimento calculado: R$" + rendimento + ". Novo saldo: R$" + saldo);
    }

    @Override
    public void exibirDados() {
        System.out.println("=== Conta Poupança ===");
        System.out.println("Titular: " + titular);
        System.out.println("Saldo: R$" + saldo);
    }
}

public class SistemaBancario {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o nome do titular da conta: ");
        String titular = scanner.nextLine();

        System.out.println("Selecione o tipo de conta:");
        System.out.println("1. Conta Corrente");
        System.out.println("2. Conta Poupança");
        int tipoConta = scanner.nextInt();

        Conta conta = null;

        if (tipoConta == 1) {
            conta = new ContaCorrente(titular);
        } else if (tipoConta == 2) {
            conta = new ContaPoupanca(titular);
        } else {
            System.out.println("Tipo de conta inválido.");
            return;
        }

        int opcao;
        do {
            System.out.println("\n=== Menu ===");
            System.out.println("1. Depositar");
            System.out.println("2. Sacar");
            if (conta instanceof ContaCorrente) {
                System.out.println("3. Usar Cheque Especial");
            } else {
                System.out.println("3. Calcular Rendimento");
            }
            System.out.println("4. Exibir Dados da Conta");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    System.out.print("Digite o valor a depositar: R$");
                    double valorDeposito = scanner.nextDouble();
                    conta.depositar(valorDeposito);
                    break;

                case 2:
                    System.out.print("Digite o valor a sacar: R$");
                    double valorSaque = scanner.nextDouble();
                    conta.sacar(valorSaque);
                    break;

                case 3:
                    if (conta instanceof ContaCorrente) {
                        System.out.print("Digite o valor a sacar com cheque especial: R$");
                        double valorChequeEspecial = scanner.nextDouble();
                        ((ContaCorrente) conta).usarChequeEspecial(valorChequeEspecial);
                    } else {
                        System.out.print("Digite a taxa Selic atual: ");
                        double selic = scanner.nextDouble();
                        ((ContaPoupanca) conta).calcularRendimento(selic);
                    }
                    break;

                case 4:
                    conta.exibirDados();
                    break;

                case 5:
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 5);

        scanner.close();
    }
}