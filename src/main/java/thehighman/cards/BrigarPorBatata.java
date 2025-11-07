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

public class BrigarPorBatata extends BaseCard {
    public static final String ID = makeID("BrigarPorBatata");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 3;

    public BrigarPorBatata() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        this.rawDescription = "Cause !D! de dano. Se o inimigo tiver Larica, remova 1 e ganhe 1 de Comido.";
        this.keywords.add("larica");
        this.keywords.add("comido");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL),
                AbstractGameAction.AttackEffect.BLUNT_LIGHT));

        if (m.hasPower(LaricaPower.POWER_ID)) {
            // Remove 1 de Larica do inimigo
            addToBot(new ReducePowerAction(m, p, LaricaPower.POWER_ID, 1));
            // Ganha 1 stack de Comido
            addToBot(new ApplyPowerAction(p, p, new ComidoPower(p, 1), 1));
        }
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);     // 6 → 9 de dano
            upgradeMagicNumber(1);         // opcional: se quiser escalar Comido ou remover mais Larica
            initializeDescription();
        }
    }
}