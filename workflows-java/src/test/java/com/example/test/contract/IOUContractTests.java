package com.example.test.contract;

import com.example.contract.ReinsuranceRenewalContract;
import com.example.state.ReinsuranceRenewalState;
import com.google.common.collect.ImmutableList;
import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.identity.CordaX500Name;
import net.corda.testing.core.TestIdentity;
import net.corda.testing.node.MockServices;
import org.junit.Test;

import static java.util.Arrays.*;

import static net.corda.testing.node.NodeTestUtils.ledger;

public class IOUContractTests {
    static private final MockServices ledgerServices = new MockServices(asList("com.example.contract", "com.example.flow"));
    static private final TestIdentity megaCorp = new TestIdentity(new CordaX500Name("MegaCorp", "London", "GB"));
    static private final TestIdentity miniCorp = new TestIdentity(new CordaX500Name("MiniCorp", "London", "GB"));
    static private final int iouValue = 1;

    @Test
    public void transactionMustIncludeCreateCommand() {
        ledger(ledgerServices, (ledger -> {
            ledger.transaction(tx -> {
                tx.output(ReinsuranceRenewalContract.ID, new ReinsuranceRenewalState(iouValue, miniCorp.getParty(), megaCorp.getParty(), new UniqueIdentifier()));
                tx.fails();
                tx.command(ImmutableList.of(megaCorp.getPublicKey(), miniCorp.getPublicKey()), new ReinsuranceRenewalContract.Commands.Create());
                tx.verifies();
                return null;
            });
            return null;
        }));
    }

    @Test
    public void transactionMustHaveNoInputs() {
        ledger(ledgerServices, (ledger -> {
            ledger.transaction(tx -> {
                tx.input(ReinsuranceRenewalContract.ID, new ReinsuranceRenewalState(iouValue, miniCorp.getParty(), megaCorp.getParty(), new UniqueIdentifier()));
                tx.output(ReinsuranceRenewalContract.ID, new ReinsuranceRenewalState(iouValue, miniCorp.getParty(), megaCorp.getParty(), new UniqueIdentifier()));
                tx.command(ImmutableList.of(megaCorp.getPublicKey(), miniCorp.getPublicKey()), new ReinsuranceRenewalContract.Commands.Create());
                tx.failsWith("No inputs should be consumed when issuing an IOU.");
                return null;
            });
            return null;
        }));
    }

    @Test
    public void transactionMustHaveOneOutput() {
        ledger(ledgerServices, (ledger -> {
            ledger.transaction(tx -> {
                tx.output(ReinsuranceRenewalContract.ID, new ReinsuranceRenewalState(iouValue, miniCorp.getParty(), megaCorp.getParty(), new UniqueIdentifier()));
                tx.output(ReinsuranceRenewalContract.ID, new ReinsuranceRenewalState(iouValue, miniCorp.getParty(), megaCorp.getParty(), new UniqueIdentifier()));
                tx.command(ImmutableList.of(megaCorp.getPublicKey(), miniCorp.getPublicKey()), new ReinsuranceRenewalContract.Commands.Create());
                tx.failsWith("Only one output state should be created.");
                return null;
            });
            return null;
        }));
    }

    @Test
    public void lenderMustSignTransaction() {
        ledger(ledgerServices, (ledger -> {
            ledger.transaction(tx -> {
                tx.output(ReinsuranceRenewalContract.ID, new ReinsuranceRenewalState(iouValue, miniCorp.getParty(), megaCorp.getParty(), new UniqueIdentifier()));
                tx.command(miniCorp.getPublicKey(), new ReinsuranceRenewalContract.Commands.Create());
                tx.failsWith("All of the participants must be signers.");
                return null;
            });
            return null;
        }));
    }

    @Test
    public void borrowerMustSignTransaction() {
        ledger(ledgerServices, (ledger -> {
            ledger.transaction(tx -> {
                tx.output(ReinsuranceRenewalContract.ID, new ReinsuranceRenewalState(iouValue, miniCorp.getParty(), megaCorp.getParty(), new UniqueIdentifier()));
                tx.command(megaCorp.getPublicKey(), new ReinsuranceRenewalContract.Commands.Create());
                tx.failsWith("All of the participants must be signers.");
                return null;
            });
            return null;
        }));
    }

    @Test
    public void lenderIsNotBorrower() {
        ledger(ledgerServices, (ledger -> {
            ledger.transaction(tx -> {
                tx.output(ReinsuranceRenewalContract.ID, new ReinsuranceRenewalState(iouValue, megaCorp.getParty(), megaCorp.getParty(), new UniqueIdentifier()));
                tx.command(ImmutableList.of(megaCorp.getPublicKey(), miniCorp.getPublicKey()), new ReinsuranceRenewalContract.Commands.Create());
                tx.failsWith("The lender and the borrower cannot be the same entity.");
                return null;
            });
            return null;
        }));
    }

    @Test
    public void cannotCreateNegativeValueIOUs() {
        ledger(ledgerServices, (ledger -> {
            ledger.transaction(tx -> {
                tx.output(ReinsuranceRenewalContract.ID, new ReinsuranceRenewalState(-1, miniCorp.getParty(), megaCorp.getParty(), new UniqueIdentifier()));
                tx.command(ImmutableList.of(megaCorp.getPublicKey(), miniCorp.getPublicKey()), new ReinsuranceRenewalContract.Commands.Create());
                tx.failsWith("The IOU's value must be non-negative.");
                return null;
            });
            return null;
        }));
    }
}