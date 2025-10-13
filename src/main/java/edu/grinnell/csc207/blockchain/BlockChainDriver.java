package edu.grinnell.csc207.blockchain;

import java.util.Scanner;
import java.security.NoSuchAlgorithmException;

/**
 * The main driver for the block chain program.
 */
public class BlockChainDriver {

    /**
     * The main entry point for the program.
     * 
     * @param args the command-line arguments
     * @throws NoSuchAlgorithmException
     * @throws NumberFormatException
     */
    public static void main(String[] args) throws NumberFormatException, NoSuchAlgorithmException {
        if (args.length == 0)
            return;
        boolean running = true;
        BlockChain b = new BlockChain(Integer.valueOf(args[0]));
        Scanner scanner = new Scanner(System.in);
        while (running) {
            System.out.println(b.toString());
            System.out.println("Command? ");
            switch (scanner.nextLine()) {
                case "mine":
                    System.out.println("Amount transferred?");
                    int amount = Integer.valueOf(scanner.nextLine());
                    Block n = b.mine(amount);
                    System.out.printf("amount = %d, nonce = %d%n", amount, n.getNonce());
                    break;
                case "append":
                    System.out.println("Amount transferred?");
                    int amt = Integer.valueOf(scanner.nextLine());
                    System.out.println("Nonce?");
                    long nonce = Long.parseLong(scanner.nextLine());
                    Block block = new Block(b.getSize(), amt, b.getHash(), nonce);
                    b.append(block);
                    break;
                case "remove":
                    b.removeLast();
                    break;
                case "check":
                    if (b.isValidBlockChain()) {
                        System.out.println("Chain is valid!");
                    } else {
                        System.out.println("Chain is not valid!");
                    }
                    break;
                case "report":
                    b.printBalance();
                    break;
                case "help":
                    System.out.println("Valid Commands:");
                    System.out.println("    append: appends a new block onto the end of the chain");
                    System.out.println("    remove: removes the last block from the end of the chain");
                    System.out.println("    check: checks that the block chain is valid");
                    System.out.println("    report: reports the balances of Alice and Bob");
                    System.out.println("    help: prints this list of commands");
                    System.out.println("    quit: quits the program");
                    break;
                case "quit":
                    running = false;
                    break;
            }
        }
        scanner.close();
    }
}
