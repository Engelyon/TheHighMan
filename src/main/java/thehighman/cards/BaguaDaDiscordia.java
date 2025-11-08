package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.cards.BadTrip;
import thehighman.character.TheHighman;
import thehighman.util.CardStats;

public class BaguaDaDiscordia extends BaseCard {
    public static final String ID = makeID("BaguaDaDiscordia");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ALL_ENEMY,
            2
    );

    private static final int DAMAGE = 10;
    private static final int UPG_DAMAGE = 4;

    public BaguaDaDiscordia() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        this.isMultiDamage = true;
        this.keywords.add("bad trip");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Causa dano a todos os inimigos
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, DamageInfo.DamageType.NORMAL,
                com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

        // Cria 2 Bad Trip e coloca no topo da pilha de descarte
        for (int i = 0; i < 2; i++) {
            AbstractCard badTrip = new BadTrip();
            badTrip.modifyCostForCombat(0); // opcional: custo 0 se quiser que seja jogável
            AbstractDungeon.player.discardPile.addToTop(badTrip);
        }
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);   // 10 → 14
            upgradeBaseCost(1);          // 2 → 1
            initializeDescription();
        }
    }
}