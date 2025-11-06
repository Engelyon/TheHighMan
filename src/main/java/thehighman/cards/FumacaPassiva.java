package thehighman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.Chapado;
import thehighman.util.CardStats;

/**
 * Representa a carta "Fumaça Passiva" no jogo.
 * Esta carta é um ataque que causa dano em área a todos os inimigos e aplica o efeito "Chapado".
 */
public class FumacaPassiva extends BaseCard {
    // Identificador único da carta
    public static final String ID = makeID("FumacaPassiva");

    // Estatísticas da carta, incluindo cor, tipo, raridade, alvo e custo de energia
    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,     // Cor da carta
            CardType.ATTACK,                // Tipo: ataque
            CardRarity.COMMON,              // Raridade
            CardTarget.ALL_ENEMY,           // Alvo: todos os inimigos
            1                               // Custo de energia
    );

    // Dano base da carta
    private static final int DAMAGE = 4;

    // Dano adicional ao aprimorar a carta
    private static final int UPG_DAMAGE = 2;

    // Valor do efeito "Chapado" aplicado pela carta
    private static final int CHAPADO = 1;

    /**
     * Construtor da carta "Fumaça Passiva".
     * Define as propriedades da carta, como dano, efeito "Chapado" e palavras-chave.
     */
    public FumacaPassiva() {
        super(ID, info);

        // Define o dano base e o dano adicional ao aprimorar
        setDamage(DAMAGE, UPG_DAMAGE);

        // Indica que o dano é em área
        isMultiDamage = true;

        // Define o valor do efeito "Chapado"
        setMagic(CHAPADO);

        // Adiciona a palavra-chave "chapado" para exibição de tooltip
        this.keywords.add("chapado");

        // Inicializa a descrição da carta com os valores dinâmicos (!D! e !M!)
        initializeDescription();
    }

    /**
     * Define o comportamento da carta quando utilizada.
     * Causa dano em área a todos os inimigos e aplica o efeito "Chapado".
     *
     * @param p O jogador que está utilizando a carta.
     * @param m O monstro alvo (não utilizado, pois a carta afeta todos os inimigos).
     */
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Causa dano a todos os inimigos na sala
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.POISON));

        // Aplica o efeito "Chapado" a todos os inimigos na sala
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            addToBot(new ApplyPowerAction(mo, p, new Chapado(mo, magicNumber), magicNumber));
        }
    }
}