package thehighman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.cards.BadTrip;
import thehighman.character.TheHighman;
import thehighman.util.CardStats;

public class Espirro extends BaseCard {
    public static final String ID = makeID("Espirro");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ALL_ENEMY,
            0
    );

    private static final int DAMAGE = 1;
    private static final int UPG_DAMAGE = 1;

    public Espirro() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        this.isMultiDamage = true;
        this.cardsToPreview = new BadTrip();
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, DamageInfo.DamageType.NORMAL,
                AbstractGameAction.AttackEffect.SMASH));
        AbstractCard badTrip = new BadTrip();
        AbstractDungeon.player.discardPile.addToTop(badTrip);
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
            initializeDescription();
        }
    }
}