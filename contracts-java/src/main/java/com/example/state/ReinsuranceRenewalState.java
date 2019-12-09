package com.example.state;

import com.example.contract.ReinsuranceRenewalContract;
import com.example.schema.ReinsuranceRenewalSchemaV1;
import com.google.common.collect.ImmutableList;
import net.corda.core.contracts.BelongsToContract;
import net.corda.core.contracts.LinearState;
import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;
import net.corda.core.schemas.MappedSchema;
import net.corda.core.schemas.PersistentState;
import net.corda.core.schemas.QueryableState;

import java.util.Arrays;
import java.util.List;

/**
 * The state object recording IOU agreements between two parties.
 *
 * A state must implement [ContractState] or one of its descendants.
 */
@BelongsToContract(ReinsuranceRenewalContract.class)
public class ReinsuranceRenewalState implements LinearState, QueryableState {



    /**
     * Define the parties
     */
    private final Party awesomeReinsurance;
    private final Party awesomeAPAC;
    private final Party awesomeIndia;
    private final String policyAmountDue;
    private final String settlementDueDate;
    private final String originalCurrency;
    private final String settlementCurrency;
    private final String status;
    private final UniqueIdentifier linearId;


    /**
     *
     * @param awesomeReinsurance the party triggereing the technical account message for premium renewal.
     * @param awesomeAPAC the intermediary party receiving and approving the IOU.
     * @param awesomeIndia the party responsible for making the premium payment.
     */

    public ReinsuranceRenewalState(Party awesomeReinsurance,
                                   Party awesomeAPAC, Party awesomeIndia, String policyAmountDue,
                                   String settlementDueDate, String originalCurrency, String settlementCurrency,
                                   String status, UniqueIdentifier linearId) {

        this.awesomeReinsurance = awesomeReinsurance;
        this.awesomeAPAC = awesomeAPAC;
        this.awesomeIndia = awesomeIndia;
        this.policyAmountDue = policyAmountDue;
        this.settlementDueDate = settlementDueDate;
        this.originalCurrency = originalCurrency;
        this.settlementCurrency = settlementCurrency;
        this.status = status;
        this.linearId = linearId;
    }


    //public Party getLender() { return lender; }
    //public Party getBorrower() { return borrower; }
    @Override public UniqueIdentifier getLinearId() { return linearId; }
    @Override public List<AbstractParty> getParticipants() {
        return Arrays.asList(awesomeReinsurance, awesomeAPAC,awesomeIndia);
    }

    public Party getAwesomeReinsurance() {
        return awesomeReinsurance;
    }

    public Party getAwesomeAPAC() {
        return awesomeAPAC;
    }

    public Party getAwesomeIndia() {
        return awesomeIndia;
    }

    public String getPolicyAmountDue() {
        return policyAmountDue;
    }

    public String getSettlementDueDate() {
        return settlementDueDate;
    }

    public String getOriginalCurrency() {
        return originalCurrency;
    }

    public String getSettlementCurrency() {
        return settlementCurrency;
    }

    public String getStatus() {
        return status;
    }

    @Override public PersistentState generateMappedObject(MappedSchema schema) {
        if (schema instanceof ReinsuranceRenewalSchemaV1) {
            return new ReinsuranceRenewalSchemaV1.PersistentIOU(
                    this.awesomeReinsurance.getName().toString(),
                    this.awesomeAPAC.getName().toString(),
                    this.awesomeIndia.getName().toString(),
                    this.policyAmountDue,
                    this.settlementDueDate,
                    this.originalCurrency,
                    this.settlementCurrency,
                    this.status,
                    this.linearId.getId());
        } else {
            throw new IllegalArgumentException("Unrecognised schema $schema");
        }
    }

    @Override public Iterable<MappedSchema> supportedSchemas() {
        return ImmutableList.of(new ReinsuranceRenewalSchemaV1());
    }


    @Override
    public String toString() {
        return "ReinsuranceRenewalState{" +
                "awesomeReinsurance=" + awesomeReinsurance +
                ", awesomeAPAC=" + awesomeAPAC +
                ", awesomeIndia=" + awesomeIndia +
                ", policyAmountDue=" + policyAmountDue +
                ", settlementDueDate=" + settlementDueDate +
                ", originalCurrency='" + originalCurrency + '\'' +
                ", settlementCurrency='" + settlementCurrency + '\'' +
                ", status='" + status + '\'' +
                ", linearId=" + linearId +
                '}';
    }
}