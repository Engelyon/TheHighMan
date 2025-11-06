package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.Seda;
import thehighman.powers.Erva;
import thehighman.util.CardStats;

public class MaoSanta extends BaseCard {
    public static final String ID = makeID("MaoSanta");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1 // custo de energia
    );

    private static final int BUFF_AMOUNT = 1;

    public MaoSanta() {
        super(ID, info);
        setMagic(BUFF_AMOUNT); // Usa magicNumber para quantidade de buffs
        this.keywords.add("Seda");
        this.keywords.add("Erva");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new Seda(p, magicNumber), magicNumber));
        addToBot(new ApplyPowerAction(p, p, new Erva(p, magicNumber), magicNumber));
    }
}