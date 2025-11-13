package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.CompreensaoElevadaPower;
import thehighman.powers.SedaPower;
import thehighman.util.CardStats;

public class CompreensaoElevada extends BaseCard {
    public static final String ID = makeID("CompreensaoElevada");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            3
    );

    public static final int MAGIC = 1;

    public CompreensaoElevada() {
        super(ID, info);
        setMagic(MAGIC);
        this.isEthereal = true;
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new CompreensaoElevadaPower(p, p, magicNumber), magicNumber));
    }

    @Override
    public void upgrade() {
        super.upgrade();
        this.isEthereal = false;
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