package br.com.alura.orgs.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.orgs.databinding.ActivityDetalhesProdutoBinding
import br.com.alura.orgs.extensions.formataParaBrasileiro
import br.com.alura.orgs.extensions.tentaCarregarImagem
import br.com.alura.orgs.model.Produto

class DetalhesProdutoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetalhesProdutoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalhesProdutoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tentaCarregarProduto()
    }

    private fun tentaCarregarProduto() {
        intent.getParcelableExtra<Produto>(CHAVE_PRODUTO)?.let { produtoCarregado ->
            preencheCampos(produtoCarregado)
        }
    }

    private fun preencheCampos(produtoCarregado: Produto) {
        with(binding) {
            produtoDetalhesImageview.tentaCarregarImagem(produtoCarregado.imagem)
            produtoDetalhesTitulo.text = produtoCarregado.nome
            produtoDetalhesDescricao.text = produtoCarregado.descricao
            produtoDetalhesValor.text = produtoCarregado.valor.formataParaBrasileiro()
        }
    }
}