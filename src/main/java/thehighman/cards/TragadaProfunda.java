package thehighman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.ComidoPower;
import thehighman.powers.LaricaPower;
import thehighman.util.CardStats;

public class TragadaProfunda extends BaseCard {
    public static final String ID = makeID("TragadaProfunda");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            2
    );

    private static final int DAMAGE = 15;
    private static final int UPG_DAMAGE = 5;
    private static final int COMIDO_COST = 2;
    private static final int LARICA_GAIN = 2;

    public TragadaProfunda() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        this.keywords.add("comido");
        this.keywords.add("larica");
        initializeDescription();
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!super.canUse(p, m)) return false;

        if (!p.hasPower(ComidoPower.POWER_ID) || p.getPower(ComidoPower.POWER_ID).amount < COMIDO_COST) {
            this.cantUseMessage = "Você precisa de pelo menos 2 de Comido.";
            return false;
        }

        return true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Causa dano
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL),
                AbstractGameAction.AttackEffect.SLASH_HEAVY));

        // Consome 2 de Comido
        addToBot(new ReducePowerAction(p, p, ComidoPower.POWER_ID, COMIDO_COST));

        // Aplica 2 de Larica ao jogador
        addToBot(new ApplyPowerAction(p, p, new LaricaPower(p, LARICA_GAIN), LARICA_GAIN));
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE); // 15 → 20 de dano
            initializeDescription();
        }
    }
}