package thehighman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import thehighman.character.TheHighman;
import thehighman.powers.ChapadoPower;
import thehighman.util.CardStats;

public class TiroCerto extends BaseCard {
    public static final String ID = makeID("TiroCerto");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 9;
    private static final int UPG_DAMAGE = 3;
    private static final int CHAPADO_THRESHOLD = 5;
    private static final int TEMP_STRENGTH = 1;

    public TiroCerto() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        this.rawDescription = "Cause !D! de dano. Se o inimigo tiver 5 ou mais de Chapado, ganhe 1 de Força temporária.";
        this.keywords.add("chapado");
        this.keywords.add("força");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Causa dano
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL),
                AbstractGameAction.AttackEffect.SLASH_HEAVY));

        // Se o inimigo tiver 5 ou mais stacks de Chapado, ganha 1 de força temporária
        if (m.hasPower(ChapadoPower.POWER_ID) && m.getPower(ChapadoPower.POWER_ID).amount >= CHAPADO_THRESHOLD) {
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, TEMP_STRENGTH), TEMP_STRENGTH));
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, -TEMP_STRENGTH), TEMP_STRENGTH));
        }
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE); // 9 → 12 de dano
            initializeDescription();
        }
    }
}