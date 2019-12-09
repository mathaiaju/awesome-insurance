package com.example.schema;

import com.google.common.collect.ImmutableList;
import net.corda.core.schemas.MappedSchema;
import net.corda.core.schemas.PersistentState;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

/**
 * An ReinsuranceRenewalState schema.
 */
public class ReinsuranceRenewalSchemaV1 extends MappedSchema {
    public ReinsuranceRenewalSchemaV1() {
        super(ReinsuranceRenewalSchema.class, 1, ImmutableList.of(PersistentIOU.class));
    }

    @Entity
    @Table(name = "iou_states")
    public static class PersistentIOU extends PersistentState {

        /**Parties **/
        @Column(name = "awesomeReinsurance") private final String awesomeReinsurance;
        @Column(name = "awesomeAPAC") private final String awesomeAPAC;
        @Column(name = "awesomeIndia") private final String awesomeIndia;


        /**Claim Reference  & Settlement Details**/
        @Column(name = "policyAmountDue") private final String policyAmountDue;
        @Column(name = "settlementDueDate") private final String settlementDueDate;
        @Column(name = "originalCurrency") private final String originalCurrency;
        @Column(name = "settlementCurrency") private final String settlementCurrency;
        @Column(name = "status") private final String status;


        @Column(name = "linear_id") private final UUID linearId;


        public PersistentIOU(String awesomeReinsurance, String awesomeAPAC,
                             String awesomeIndia, String policyAmountDue, String settlementDueDate,
                             String originalCurrency, String settlementCurrency , String status, UUID linearId) {
            this.awesomeReinsurance = awesomeReinsurance;
            this.awesomeAPAC = awesomeAPAC;
            this.awesomeIndia =awesomeIndia;
            this.policyAmountDue=policyAmountDue;
            this.settlementDueDate=settlementDueDate;
            this.originalCurrency=originalCurrency;
            this.settlementCurrency=settlementCurrency;
            this.status=status;
            this.linearId = linearId;
        }

        // Default constructor required by hibernate.
        public PersistentIOU() {
            this.awesomeReinsurance=null;
            this.awesomeAPAC=null;
            this.awesomeIndia=null;
            this.policyAmountDue=null;
            this.settlementDueDate=null;
            this.originalCurrency=null;
            this.settlementCurrency=null;
            this.status=null;
            this.linearId=null;
        }

        public String getAwesomeReinsurance() {
            return awesomeReinsurance;
        }

        public String getAwesomeAPAC() {
            return awesomeAPAC;
        }

        public String getAwesomeIndia() {
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

        public UUID getId() {
            return linearId;
        }
    }
}