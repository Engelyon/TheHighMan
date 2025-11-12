package thehighman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.ComidoPower;
import thehighman.util.CardStats;

public class LaricaDePoder extends BaseCard {
    public static final String ID = makeID("LaricaDePoder");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            2
    );

    // Não há dano base fixo; o dano será calculado por stack de Comido
    private static final int DAMAGE_PER_COMIDO = 5;   // valor base por stack
    private static final int UPG_DAMAGE_PER_COMIDO = 10; // valor por stack quando upada

    public LaricaDePoder() {
        super(ID, info);
        // define damage base como 0 para que !D! mostre 0 por padrão (ou você pode omitir)
        setDamage(0, 0);
        this.keywords.add("comido");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int stacks = 0;
        if (p.hasPower(ComidoPower.POWER_ID)) {
            stacks = p.getPower(ComidoPower.POWER_ID).amount;
        }

        if (stacks <= 0) {
            // Sem Comido: não causa dano; se preferir causar um valor mínimo, ajuste aqui
            return;
        }

        // calcula dano por stack considerando upgrade
        int perStack = upgraded ? UPG_DAMAGE_PER_COMIDO : DAMAGE_PER_COMIDO;
        int totalDamage = stacks * perStack;

        // Remove todos os stacks de Comido
        addToBot(new ReducePowerAction(p, p, ComidoPower.POWER_ID, stacks));

        // Causa dano total calculado
        addToBot(new DamageAction(m,
                new DamageInfo(p, totalDamage, DamageInfo.DamageType.NORMAL),
                AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!super.canUse(p, m)) {
            return false;
        }

        if (p == null || !p.hasPower(ComidoPower.POWER_ID) || p.getPower(ComidoPower.POWER_ID).amount <= 0) {
            this.cantUseMessage = "Preciso de pelo menos 1 de Comido.";
            return false;
        }

        return true;
    }

    @Override
    public void upgrade() {
        super.upgrade();
    }
}