package com.example.android.kanaking;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.kanaking.model.ItemPedido;
import com.example.android.kanaking.model.Pedido;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.kanaking.Constantes.CANCELADO;
import static com.example.android.kanaking.Constantes.CARTAO;
import static com.example.android.kanaking.Constantes.DINHEIRO;
import static com.example.android.kanaking.Constantes.LANCADO;
import static com.example.android.kanaking.Constantes.PREPARANDO;
import static com.example.android.kanaking.Constantes.PRONTO;
import static com.example.android.kanaking.Constantes.TERMINADO;

public class PedidoAdapter extends ArrayAdapter<Pedido> {
    private Context mContext;
    private List<Pedido> pedidosList = new ArrayList<>();

    public PedidoAdapter(@NonNull Context context, @LayoutRes ArrayList<Pedido> list) {
        super(context, 0 , list);
        mContext = context;
        pedidosList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        Pedido pedidoAtual = pedidosList.get(position);

        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.pedido,parent,false);

        TextView comanda = (TextView)listItem.findViewById(R.id.comanda);
        comanda.setText(String.valueOf(pedidoAtual.getComanda()));

        //Configurando GridView
        GridView itemGrid = listItem.findViewById(R.id.itens);
        ItemPedidoAdapter itemAdapter = new ItemPedidoAdapter(mContext,pedidoAtual.getItemPedidos());
        itemGrid.setAdapter(itemAdapter);

        itemGrid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext, "Clique longo",Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        itemGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext, "Clique simples",Toast.LENGTH_SHORT).show();
            }

        });

        TextView valor = (TextView)listItem.findViewById(R.id.valor);
        valor.setText(String.valueOf(pedidoAtual.getValor()));

        ImageView tipoPagamento = (ImageView)listItem.findViewById(R.id.tipo_pagamento);
        switch(pedidoAtual.getFormaPagamento()){
            case CARTAO:
                tipoPagamento.setImageResource(R.drawable.cartao);
                break;
            case DINHEIRO:
                tipoPagamento.setImageResource(R.drawable.dinheiro);
        }


        LinearLayout fundo = (LinearLayout)listItem.findViewById(R.id.fundo);
        switch(pedidoAtual.getEstado()){
            case LANCADO://cores escolhidas com a ajuda de http://erikasarti.com/html/tabela-cores/
                fundo.setBackgroundResource(R.color.corLancado);
                break;
            case PREPARANDO:
                fundo.setBackgroundResource(R.color.corPreparando);
                break;
            case PRONTO:
                fundo.setBackgroundResource(R.color.corPronto);
                break;
            case CANCELADO:
                fundo.setBackgroundResource(R.color.corCancelado);
                break;
            case TERMINADO:
                fundo.setBackgroundResource(R.color.corTerminado);
                break;
        }


        return listItem;
    }
}
