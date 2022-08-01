package br.com.alura.orgs.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

/*Para usar essa anotação, devemos adicionar um plugin nas dependências do projeto. Ela é útil para criar uma implementação gerada automaticamente, sem haver a necessidade de
* implementar métodos da classe Parcelable*/
@Entity
@Parcelize
data class Produto(
        @PrimaryKey(autoGenerate = true) val id: Long = 0L,
        val nome: String,
        val descricao: String,
        val valor: BigDecimal,
        val imagem: String? = null
): Parcelable
