package com.github.aberdar.blockchain;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BlockUnitTest {

    // Цепочка блоков
    public static List<Block> blockchain = new ArrayList<Block>();
    public static int index = 4;
    public static String indexString = new String(new char[index]).replace('\0', '0');

    @BeforeClass
    public static void setUp() {
        Block block = new Block("The is Block.",
                "0",
                new Date().getTime());
        block.blockMining(index);
        blockchain.add(block);

        Block firstBlock = new Block("The is the First Block",
                block.getHash(),
                new Date().getTime());
        firstBlock.blockMining(index);
        blockchain.add(firstBlock);
    }

    @Test
    public void newBlockAdded() {
        Block newBlock = new Block("New Block",
                blockchain.get(blockchain.size() - 1).getHash(),
                new Date().getTime()
        );
        newBlock.blockMining(index);
        assertTrue(newBlock.getHash()
                .substring(0, index)
                .equals(indexString));
        blockchain.add(newBlock);
    }

    @Test
    public void givenBlockchainValidated() {
        boolean flag = true;
        for (int i = 0; i < blockchain.size(); i++) {
            String previousHash = i == 0 ? "0"
                    : blockchain.get(i - 1)
                    .getHash();
            flag = blockchain.get(i)
                    .getHash()
                    .equals(blockchain.get(i)
                            .calculationHashBlock())
                    && previousHash.equals(blockchain.get(i)
                    .getPreviousHash())
                    && blockchain.get(i)
                    .getHash()
                    .substring(0, index)
                    .equals(indexString);
            if (!flag)
                break;
        }
        assertTrue(flag);
    }

    @AfterClass
    public static void tearDown() {
        blockchain.clear();
    }
}
