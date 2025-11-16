package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.ChapadoPower;
import thehighman.powers.SedaPower;
import thehighman.util.CardStats;

public class BrisaFinal extends BaseCard {
    public static final String ID = makeID("BrisaFinal");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ALL_ENEMY,
            -1 // X-cost
    );

    private static final int DAMAGE = 5;
    private static final int UPG_X_PLUS = 1;
    private static final int CHAPADO_AMOUNT = 1;

    public BrisaFinal() {
        super(ID, info);
        setDamage(DAMAGE);
        setMagic(CHAPADO_AMOUNT);
        this.isMultiDamage = true;
        this.keywords.add("chapado");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int effect = this.energyOnUse;
        if (p.hasRelic("Chemical X")) {
            effect += 2;
            p.getRelic("Chemical X").flash();
        }
        if (upgraded) {
            effect += UPG_X_PLUS;
        }
        if (effect > 0) {
            for (int i = 0; i < effect; i++) {
                addToBot(new DamageAllEnemiesAction(p, this.multiDamage, DamageInfo.DamageType.NORMAL,
                        com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (!mo.isDeadOrEscaped()) {
                        addToBot(new ApplyPowerAction(mo, p, new ChapadoPower(mo, this.magicNumber), this.magicNumber));
                    }
                }
                addToBot(new WaitAction(0.1f));
            }
        }
        if (!this.freeToPlayOnce) {
            p.energy.use(this.energyOnUse);
        }
    }

    @Override
    public void upgrade() {
        super.upgrade();
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        int base = this.baseMagicNumber;
        int newMagic = base;
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower(SedaPower.POWER_ID)) {
            int seda = AbstractDungeon.player.getPower(SedaPower.POWER_ID).amount;
            newMagic = base + Math.max(0, seda);
        }
        this.magicNumber = newMagic;
        this.isMagicNumberModified = (this.magicNumber != this.baseMagicNumber);
        initializeDescription();
    }
}