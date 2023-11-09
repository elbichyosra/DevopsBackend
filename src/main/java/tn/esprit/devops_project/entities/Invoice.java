package tn.esprit.devops_project.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class  Invoice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NonNull
	Long idInvoice;
	@NonNull
	float amountDiscount;
	@NonNull
	float amountInvoice;
	@Temporal(TemporalType.DATE)
	@Pattern(regexp = "yyyy-mm-dd")

	Date dateCreationInvoice;
	@Temporal(TemporalType.DATE)

	@Pattern(regexp = "yyyy-mm-dd")
	Date dateLastModificationInvoice;
	@NonNull
	Boolean archived;
	@OneToMany(mappedBy = "invoice")
	@JsonIgnore
	Set<InvoiceDetail> invoiceDetails;
    @ManyToOne
	@JsonIgnore
    Supplier supplier;


}
