package valueobject;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Embeddable
public class DataNascimento {

    @Temporal(TemporalType.DATE)
    private Date data;

    protected DataNascimento() {
    }

    public DataNascimento(String dataStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        try {
            this.data = sdf.parse(dataStr);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Formato de data inválido. Use dd/MM/yyyy.");
        }
        if (this.data.after(new Date())) {
            throw new IllegalArgumentException("A data de nascimento não pode ser futura.");
        }
    }

    public Date getData() {
        return new Date(data.getTime());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataNascimento that)) return false;
        return data.equals(that.data);
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(data);
    }
}
