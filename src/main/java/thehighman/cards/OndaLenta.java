package thehighman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thehighman.character.TheHighman;
import thehighman.powers.ChapadoPower;
import thehighman.powers.ChapadoProximoTurnoPower;
import thehighman.powers.SedaPower;
import thehighman.util.CardStats;

public class OndaLenta extends BaseCard {
    public static final String ID = makeID("OndaLenta");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 3;
    private static final int CHAPADO_AMOUNT = 1;

    public OndaLenta() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(CHAPADO_AMOUNT);
        this.exhaust = true;
        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(CHAPADO_AMOUNT);
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL),
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new ApplyPowerAction(m, p, new ChapadoPower(m, this.magicNumber), this.magicNumber));
        int delayedTurns = upgraded ? 2 : 1;
        addToBot(new ApplyPowerAction(m, p, new ChapadoProximoTurnoPower(m, delayedTurns, this.magicNumber), this.magicNumber));
    }

    @Override
    public void upgrade() {
        super.upgrade();
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if (AbstractDungeon.player.hasPower(SedaPower.POWER_ID)) {
            int seda = AbstractDungeon.player.getPower(SedaPower.POWER_ID).amount;
            int bonus = Math.max(0, seda);
            this.magicNumber = this.baseMagicNumber + bonus;
            isMagicNumberModified = true;
        } else {
            this.magicNumber = this.baseMagicNumber;
            isMagicNumberModified = false;
        }
        initializeDescription();
    }
}