package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import thehighman.character.TheHighman;
import thehighman.powers.Chapado;
import thehighman.util.CardStats;

public class CheirinhoBom extends BaseCard {
    public static final String ID = makeID("CheirinhoBom");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DEX_GAIN = 1;
    private static final int UPG_DEX = 1;

    public CheirinhoBom() {
        super(ID, info);
        setMagic(DEX_GAIN, UPG_DEX);
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m.hasPower(Chapado.POWER_ID)) {
            addToBot(new ApplyPowerAction(p, p,
                    new DexterityPower(p, this.magicNumber), this.magicNumber));
        }
    }
}