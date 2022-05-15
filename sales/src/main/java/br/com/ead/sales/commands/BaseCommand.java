package br.com.ead.sales.commands;

import br.com.ead.commons.Message;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public abstract class BaseCommand extends Message {
}