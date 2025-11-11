package thehighman.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.HashMap;
import java.util.Map;

import static thehighman.InimigosDoSpire.makeID;

public class CompreensaoElevadaPower extends BasePower {
    public static final String POWER_ID = makeID("CompreensaoElevadaPower");

    // amount = multiplicador (2 = dobrar)
    public CompreensaoElevadaPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, PowerType.BUFF, false, owner, source, amount);
    }

    public CompreensaoElevadaPower(AbstractCreature owner, AbstractCreature source) {
        this(owner, source, 2);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    // Map para rastrear quantos "extras" foram enfileirados para cada alvo
    // (chave: alvo; valor: quantos stacks extras ainda aguardam ser aplicados)
    private final Map<AbstractCreature, Integer> queuedExtras = new HashMap<>();

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        // checagens de segurança
        if (power == null || power.ID == null) return;
        if (target == null || source == null) return;

        // só interessam aplicações do Chapado
        if (!power.ID.equals(ChapadoPower.POWER_ID)) return;

        // se este evento é resultado dos extras que nós enfileiramos, consome o contador e retorna
        Integer queued = queuedExtras.get(target);
        if (queued != null && queued > 0) {
            // consome um extra esperado (um dos ApplyPowerAction que nós enfileiramos)
            int novo = queued - 1;
            if (novo <= 0) queuedExtras.remove(target);
            else queuedExtras.put(target, novo);
            return; // não processa duplicação para aplicações que são nossos extras
        }

        // agora: este é o evento da aplicação "original" — checa se foi aplicado pelo jogador (owner)
        if (source != this.owner) return;   // só duplicar quando owner aplicou
        if (target == this.owner) return;   // não duplicar auto-aplicações em si mesmo

        // calcula quantos foram aplicados na ação original (fallback 1)
        int baseApplied = (power.amount > 0) ? power.amount : 1;

        // quanto queremos no total: baseApplied * multiplicador (this.amount)
        int totalWanted = baseApplied * Math.max(1, this.amount);
        int extraToApply = totalWanted - baseApplied;
        if (extraToApply <= 0) return;

        // registra no map que enfileiramos 'extraToApply' aplicacoes extras para esse alvo
        queuedExtras.put(target, queuedExtras.getOrDefault(target, 0) + extraToApply);

        // enfileira a ApplyPowerAction extra (uma única ApplyPowerAction com amount = extraToApply)
        // a action que for executada vai disparar onApplyPower para cada power aplicada, e os
        // callbacks subsequentes vão consumir o contador acima, evitando recursão.
        addToBot(new ApplyPowerAction(target, this.owner, new ChapadoPower(target, extraToApply), extraToApply));
    }
}