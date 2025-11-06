package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.Seda_naofunciona;
import thehighman.powers.Erva_talvezfuncione;
import thehighman.util.CardStats;

/**
 * Represents the "Mao Santa" card in the game.
 * This card is a skill card that applies the "Seda" and "Erva" powers to the player.
 */
public class MaoSanta extends BaseCard {
    // Unique identifier for the card
    public static final String ID = makeID("MaoSanta");

    // Card statistics such as color, type, rarity, target, and cost
    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    // Base amount of the buff applied by the card
    private static final int BUFF_AMOUNT = 1;

    // Additional buff amount when the card is upgraded
    private static final int UPG_BUFF = 1;

    /**
     * Constructor for the "Mao Santa" card.
     * Initializes the card's properties, including its magic number and tooltips.
     */
    public MaoSanta() {
        super(ID, info);

        // Sets the magic number (buff amount) and its upgrade value
        setMagic(BUFF_AMOUNT, UPG_BUFF);

        // Adds tooltips for the powers applied by this card
        this.keywords.add("seda");
        this.keywords.add("erva");

        // Finalizes the card's description
        initializeDescription();
    }

    /**
     * Defines the actions performed when the card is used.
     * Applies the "Seda" and "Erva" powers to the player.
     *
     * @param p The player using the card.
     * @param m The target monster (not used as this card targets the player).
     */
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Applies the "Seda" power to the player
        addToBot(new ApplyPowerAction(p, p, new Seda_naofunciona(p, magicNumber), magicNumber));

        // Applies the "Erva" power to the player
        addToBot(new ApplyPowerAction(p, p, new Erva_talvezfuncione(p, magicNumber), magicNumber));
    }
}