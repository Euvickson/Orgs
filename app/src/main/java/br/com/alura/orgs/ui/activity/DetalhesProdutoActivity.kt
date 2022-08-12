package br.com.alura.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.orgs.R
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityDetalhesProdutoBinding
import br.com.alura.orgs.extensions.formataParaBrasileiro
import br.com.alura.orgs.extensions.tentaCarregarImagem
import br.com.alura.orgs.model.Produto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetalhesProdutoActivity : AppCompatActivity() {


    private var idProduto: Long = 0L
    private var produto: Produto? = null
    private lateinit var binding: ActivityDetalhesProdutoBinding

    private val produtoDao by lazy {
        AppDatabase.instancia(this).produtoDao()
    }
    private val scope = CoroutineScope(Dispatchers.IO)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalhesProdutoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tentaCarregarProduto()
    }

    override fun onResume() {
        super.onResume()
        buscaProdutoNoBanco()
    }

    private fun buscaProdutoNoBanco() {
        scope.launch {
            produto = produtoDao.buscaPorId(idProduto)
            withContext(Dispatchers.Main){
                produto?.let {
                    preencheCampos(it)
                } ?: finish()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalhes_produto, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_detalhes_produto_remover -> {
                scope.launch {
                    produto?.let { produtoDao.remove(it) }
                }
                finish()
            }
            R.id.menu_detalhes_produto_editar -> {
                Intent(this, FormularioProdutoActivity::class.java).apply {
                    putExtra(CHAVE_PRODUTO_ID, idProduto)
                    startActivity(this)

                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun tentaCarregarProduto() {
        idProduto = intent.getLongExtra(CHAVE_PRODUTO_ID, 0L)
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