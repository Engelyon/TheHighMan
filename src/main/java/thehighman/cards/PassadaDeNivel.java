package thehighman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.ChapadoPower;
import thehighman.powers.ErvaPower;
import thehighman.util.CardStats;

public class PassadaDeNivel extends BaseCard {
    public static final String ID = makeID("PassadaDeNivel");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public static int DRAW = 2;
        public static int UPG_DRAW = 1;
        public static int C_ERVA = 2;
        public static int ENERGIA = 1;

    public PassadaDeNivel() {
        super(ID, info);
        setMagic(DRAW, UPG_DRAW);
        this.keywords.add("chapado");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(p, this.magicNumber));
        int ervaStacks = p.getPower(ErvaPower.POWER_ID).amount;
        if (ervaStacks >= C_ERVA) {
            addToBot(new com.megacrit.cardcrawl.actions.common.GainEnergyAction(ENERGIA));
        }
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_DRAW);
            initializeDescription();
        }
    }
}